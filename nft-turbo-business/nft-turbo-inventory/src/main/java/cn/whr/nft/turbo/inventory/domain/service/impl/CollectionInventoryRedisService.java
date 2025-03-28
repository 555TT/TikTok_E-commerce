package cn.whr.nft.turbo.inventory.domain.service.impl;

import cn.whr.nft.turbo.api.inventory.request.InventoryRequest;
import org.springframework.stereotype.Service;

/**
 * @author whr
 */
@Service
public class CollectionInventoryRedisService extends AbstraceInventoryRedisService {

    private static final String INVENTORY_KEY = "clc:inventory:";

    private static final String INVENTORY_STREAM_KEY = "clc:inventory:stream:";

    @Override
    protected String getCacheKey(InventoryRequest request) {
        return INVENTORY_KEY + request.getGoodsId();
    }

    @Override
    protected String getCacheStreamKey(InventoryRequest request) {
        return INVENTORY_STREAM_KEY + request.getGoodsId();
    }
}
