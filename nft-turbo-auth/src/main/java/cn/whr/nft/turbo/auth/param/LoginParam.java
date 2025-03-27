package cn.whr.nft.turbo.auth.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Setter
@Getter
public class LoginParam extends RegisterParam {

    /**
     * 记住我
     */
    private Boolean rememberMe;
}
