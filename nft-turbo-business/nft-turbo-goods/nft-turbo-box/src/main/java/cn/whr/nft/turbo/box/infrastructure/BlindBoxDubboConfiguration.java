package cn.whr.nft.turbo.box.infrastructure;

import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.whr.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whr
 */
@Configuration
public class BlindBoxDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @DubboReference(version = "1.0.0")
    private ChainFacadeService chainFacadeService;

    @DubboReference(version = "1.0.0")
    private CollectionReadFacadeService collectionReadFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "orderFacadeService")
    public OrderFacadeService orderFacadeService() {
        return orderFacadeService;
    }


    @Bean
    @ConditionalOnMissingBean(name = "chainFacadeService")
    public ChainFacadeService chainFacadeService() {
        return chainFacadeService;
    }


    @Bean
    @ConditionalOnMissingBean(name = "collectionFacadeService")
    public CollectionReadFacadeService collectionFacadeService() {
        return collectionReadFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "inventoryFacadeService")
    public InventoryFacadeService inventoryFacadeService() {
        return inventoryFacadeService;
    }
}
