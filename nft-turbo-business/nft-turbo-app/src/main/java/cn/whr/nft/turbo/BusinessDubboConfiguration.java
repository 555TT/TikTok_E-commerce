package cn.whr.nft.turbo;

import cn.whr.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.whr.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.whr.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.pay.service.PayFacadeService;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo配置
 *
 * @author whr
 */
@Configuration
public class BusinessDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private ChainFacadeService chainFacadeService;

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @DubboReference(version = "1.0.0")
    private PayFacadeService payFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @DubboReference(version = "1.0.0")
    private CollectionReadFacadeService collectionReadFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private BlindBoxManageFacadeService blindBoxManageFacadeService;

    @DubboReference(version = "1.0.0")
    private BlindBoxReadFacadeService blindBoxReadFacadeService;


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
    @ConditionalOnMissingBean(name = "chainFacadeService")
    public ChainFacadeService chainFacadeService() {
        return chainFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "goodsFacadeService")
    public GoodsFacadeService goodsFacadeService() {
        return goodsFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "blindBoxManageFacadeService")
    public BlindBoxManageFacadeService blindBoxManageFacadeService() {
        return blindBoxManageFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "blindBoxReadFacadeService")
    public BlindBoxReadFacadeService blindBoxReadFacadeService() {
        return blindBoxReadFacadeService;
    }

}
