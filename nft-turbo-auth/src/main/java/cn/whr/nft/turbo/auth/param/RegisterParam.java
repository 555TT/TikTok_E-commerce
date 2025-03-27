package cn.whr.nft.turbo.auth.param;

import cn.whr.nft.turbo.base.validator.IsMobile;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Setter
@Getter
public class RegisterParam {

    /**
     * 手机号
     */
    @IsMobile
    private String telephone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    /**
     * 邀请码
     */
    private String inviteCode;
}
