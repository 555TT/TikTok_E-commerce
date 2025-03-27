package cn.whr.nft.turbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whr
 */
@SpringBootApplication(scanBasePackages = "cn.whr.nft.turbo")
public class NfTurboBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboBusinessApplication.class, args);
    }

}
