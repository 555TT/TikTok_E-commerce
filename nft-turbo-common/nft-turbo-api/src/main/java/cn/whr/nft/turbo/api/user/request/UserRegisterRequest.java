package cn.whr.nft.turbo.api.user.request;

import cn.whr.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * @author whr
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest extends BaseRequest {

    private String telephone;

    private String inviteCode;

    private String password;

}
