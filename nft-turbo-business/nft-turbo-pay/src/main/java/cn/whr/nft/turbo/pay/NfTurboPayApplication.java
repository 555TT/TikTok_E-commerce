package cn.whr.nft.turbo.pay;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whr
 */
@SpringBootApplication(scanBasePackages = "cn.whr.nft.turbo.pay")
@EnableDubbo
public class NfTurboPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(NfTurboPayApplication.class, args);
    }

}
