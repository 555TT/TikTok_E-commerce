package cn.whr.nft.turbo.user.facade;

import cn.whr.nft.turbo.api.user.request.UserRegisterRequest;
import cn.whr.nft.turbo.api.user.response.UserOperatorResponse;
import cn.whr.nft.turbo.api.user.service.UserManageFacadeService;
import cn.whr.nft.turbo.rpc.facade.Facade;
import cn.whr.nft.turbo.user.domain.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author whr
 */
@DubboService(version = "1.0.0")
public class UserManageFacadeServiceImpl implements UserManageFacadeService {

    @Autowired
    private UserService userService;

    @Override
    @Facade
    public UserOperatorResponse registerAdmin(UserRegisterRequest userRegisterRequest) {
        return userService.registerAdmin(userRegisterRequest.getTelephone(), userRegisterRequest.getPassword());
    }

    @Override
    public UserOperatorResponse freeze(Long userId) {
        return userService.freeze(userId);
    }

    @Override
    public UserOperatorResponse unfreeze(Long userId) {
        return userService.unfreeze(userId);
    }
}
