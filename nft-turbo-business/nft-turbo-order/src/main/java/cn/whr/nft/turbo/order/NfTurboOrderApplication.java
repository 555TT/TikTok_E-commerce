package cn.whr.nft.turbo.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whr
 */
@SpringBootApplication(scanBasePackages = "cn.whr.nft.turbo.order")
@EnableDubbo
public class NfTurboOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboOrderApplication.class, args);
    }

}
