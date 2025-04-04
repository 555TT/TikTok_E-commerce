package cn.whr.nft.turbo.api.user.request;

import cn.whr.nft.turbo.base.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author whr
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest extends BaseRequest {

    private Long userId;
    private String realName;
    private String idCard;

}
