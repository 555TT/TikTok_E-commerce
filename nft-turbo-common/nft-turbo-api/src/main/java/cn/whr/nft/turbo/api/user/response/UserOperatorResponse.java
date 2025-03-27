package cn.whr.nft.turbo.api.user.response;

import cn.whr.nft.turbo.api.user.response.data.UserInfo;
import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户操作响应
 *
 * @author whr
 */
@Getter
@Setter
public class UserOperatorResponse extends BaseResponse {

    private UserInfo user;
}
