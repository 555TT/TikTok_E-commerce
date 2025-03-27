package cn.whr.nft.turbo.inventory.domain.service.impl;

import cn.whr.nft.turbo.api.inventory.request.InventoryRequest;
import cn.whr.nft.turbo.inventory.domain.response.InventoryResponse;
import cn.whr.nft.turbo.inventory.domain.service.InventoryService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.client.codec.IntegerCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static cn.whr.nft.turbo.base.response.ResponseCode.BIZ_ERROR;
import static cn.whr.nft.turbo.base.response.ResponseCode.DUPLICATED;

/**
 * 库存服务通用实现-基于Redis
 *
 * @author whr
 */
public abstract class AbstraceInventoryRedisService implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(AbstraceInventoryRedisService.class);

    @Autowired
    private RedissonClient redissonClient;

    private static final String ERROR_CODE_INVENTORY_NOT_ENOUGH = "INVENTORY_NOT_ENOUGH";
    private static final String ERROR_CODE_KEY_NOT_FOUND = "KEY_NOT_FOUND";
    private static final String ERROR_CODE_OPERATION_ALREADY_EXECUTED = "OPERATION_ALREADY_EXECUTED";

    @Override
    public InventoryResponse init(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            inventoryResponse.setSuccess(true);
            inventoryResponse.setResponseCode(DUPLICATED.name());
            return inventoryResponse;
        }
        redissonClient.getBucket(getCacheKey(request)).set(request.getInventory());
        inventoryResponse.setSuccess(true);
        inventoryResponse.setGoodsId(request.getGoodsId());
        inventoryResponse.setGoodsType(request.getGoodsType());
        inventoryResponse.setIdentifier(request.getIdentifier());
        inventoryResponse.setInventory(request.getInventory());
        return inventoryResponse;
    }

    @Override
    public Integer getInventory(InventoryRequest request) {
        Integer stock = (Integer) redissonClient.getBucket(getCacheKey(request), IntegerCodec.INSTANCE).get();
        return stock;
    }

    @Override
    public InventoryResponse decrease(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        String luaScript = """
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                                
                local current = redis.call('get', KEYS[1])
                if current == false then
                    return redis.error_reply('KEY_NOT_FOUND')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                if tonumber(current) < tonumber(ARGV[1]) then
                    return redis.error_reply('INVENTORY_NOT_ENOUGH')
                end
                                
                local new = tonumber(current) - tonumber(ARGV[1])
                redis.call('set', KEYS[1], tostring(new))
                                
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                                
                -- 使用哈希结构存储日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "decrease",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
                                
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                    luaScript,
                    RScript.ReturnType.INTEGER,
                    Arrays.asList(getCacheKey(request), getCacheStreamKey(request)),
                    request.getInventory(), "DECREASE_" + request.getIdentifier())).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            logger.error("decrease error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_INVENTORY_NOT_ENOUGH)) {
                inventoryResponse.setResponseCode(ERROR_CODE_INVENTORY_NOT_ENOUGH);
            } else if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    @Override
    public String getInventoryDecreaseLog(InventoryRequest request) {
        String luaScript = """
                local jsonString = redis.call('hget', KEYS[1], ARGV[1])
                return jsonString
                """;

        String stream = redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                luaScript,
                RScript.ReturnType.STATUS,
                Arrays.asList(getCacheStreamKey(request)), "DECREASE_" + request.getIdentifier());
        return stream;
    }

    @Override
    public InventoryResponse increase(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        String luaScript = """
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                                
                local current = redis.call('get', KEYS[1])
                if current == false then
                    return redis.error_reply('key not found')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                                
                local new = (current == nil and 0 or tonumber(current)) + tonumber(ARGV[1])
                redis.call('set', KEYS[1], tostring(new))
                                
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                                
                -- 使用哈希结构存储日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "increase",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
                                
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                    luaScript,
                    RScript.ReturnType.INTEGER,
                    Arrays.asList(getCacheKey(request), getCacheStreamKey(request)),
                    request.getInventory(), "INCREASE_" + request.getIdentifier())).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            logger.error("increase error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    @Override
    public void invalid(InventoryRequest request) {
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            redissonClient.getBucket(getCacheKey(request)).delete();
        }

        if (redissonClient.getBucket(getCacheStreamKey(request)).isExists()) {
            // 让流水记录的过期时间设置为24小时后，这样可以避免流水记录立即过期，对账出现问题
            redissonClient.getBucket(getCacheStreamKey(request)).expire(Instant.now().plus(24, ChronoUnit.HOURS));
        }
    }

    protected abstract String getCacheKey(InventoryRequest request);

    protected abstract String getCacheStreamKey(InventoryRequest request);
}
