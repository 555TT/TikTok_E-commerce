package cn.whr.nft.turbo.api.user.service;

import cn.whr.nft.turbo.api.user.request.UserRegisterRequest;
import cn.whr.nft.turbo.api.user.response.UserOperatorResponse;

/**
 * @author whr
 */
public interface UserManageFacadeService {

    /**
     * 管理用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    UserOperatorResponse registerAdmin(UserRegisterRequest userRegisterRequest);

    /**
     * 用户冻结
     *
     * @param userId
     * @return
     */
    UserOperatorResponse freeze(Long userId);

    /**
     * 用户解冻
     *
     * @param userId
     * @return
     */
    UserOperatorResponse unfreeze(Long userId);

}
