package cn.whr.nft.turbo.trade.infrastructure.config;

import cn.whr.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.whr.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.pay.service.PayFacadeService;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whr
 */
@Configuration
public class TradeDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @DubboReference(version = "1.0.0")
    private PayFacadeService payFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;


    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "payFacadeService")
    public PayFacadeService payFacadeService() {
        return payFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "orderFacadeService")
    public OrderFacadeService orderFacadeService() {
        return orderFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "goodsFacadeService")
    public GoodsFacadeService goodsFacadeService() {
        return goodsFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "inventoryFacadeService")
    public InventoryFacadeService inventoryFacadeService() {
        return inventoryFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }
}
