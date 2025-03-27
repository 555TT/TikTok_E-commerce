package cn.whr.nft.turbo.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whr
 */
@SpringBootApplication(scanBasePackages = {"cn.whr.nft.turbo.goods","cn.whr.nft.turbo.collection","cn.whr.nft.turbo.box"})
@EnableDubbo(scanBasePackages = {"cn.whr.nft.turbo.goods","cn.whr.nft.turbo.collection","cn.whr.nft.turbo.box"})
public class NfTurboGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboGoodsApplication.class, args);
    }

}
