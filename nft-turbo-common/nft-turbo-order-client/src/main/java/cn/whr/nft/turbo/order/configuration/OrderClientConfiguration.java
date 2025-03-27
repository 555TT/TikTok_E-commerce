package cn.whr.nft.turbo.order.configuration;

import cn.whr.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.whr.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.order.sharding.id.WorkerIdHolder;
import cn.whr.nft.turbo.order.validator.GoodsBookValidator;
import cn.whr.nft.turbo.order.validator.GoodsValidator;
import cn.whr.nft.turbo.order.validator.StockValidator;
import cn.whr.nft.turbo.order.validator.UserValidator;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whr
 */
@Configuration
public class OrderClientConfiguration {

    @Bean
    public WorkerIdHolder workerIdHolder(RedissonClient redisson) {
        return new WorkerIdHolder(redisson);
    }

    @Bean
    public GoodsValidator goodsValidator(GoodsFacadeService goodsFacadeService) {
        return new GoodsValidator(goodsFacadeService);
    }

    @Bean
    public StockValidator stockValidator(InventoryFacadeService inventoryFacadeService) {
        return new StockValidator(inventoryFacadeService);
    }

    @Bean
    public UserValidator userValidator(UserFacadeService userFacadeService) {
        return new UserValidator(userFacadeService);
    }

    @Bean
    public GoodsBookValidator goodsBookValidator(GoodsFacadeService goodsFacadeService) {
        return new GoodsBookValidator(goodsFacadeService);
    }
}
