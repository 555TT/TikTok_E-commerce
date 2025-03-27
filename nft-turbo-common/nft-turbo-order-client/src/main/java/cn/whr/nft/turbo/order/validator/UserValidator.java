package cn.whr.nft.turbo.order.validator;

import cn.whr.nft.turbo.api.order.request.OrderCreateRequest;
import cn.whr.nft.turbo.api.user.constant.UserRole;
import cn.whr.nft.turbo.api.user.constant.UserStateEnum;
import cn.whr.nft.turbo.api.user.request.UserQueryRequest;
import cn.whr.nft.turbo.api.user.response.UserQueryResponse;
import cn.whr.nft.turbo.api.user.response.data.UserInfo;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.order.OrderException;

import static cn.whr.nft.turbo.api.order.constant.OrderErrorCode.*;

/**
 * 用户校验器
 *
 * @author whr
 */
public class UserValidator extends BaseOrderCreateValidator {

    private UserFacadeService userFacadeService;

    @Override
    public void doValidate(OrderCreateRequest request) throws OrderException {
        String buyerId = request.getBuyerId();
        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(buyerId));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        if (userQueryResponse.getSuccess() && userQueryResponse.getData() != null) {
            UserInfo userInfo = userQueryResponse.getData();
            if (userInfo.getUserRole() != null && !userInfo.getUserRole().equals(UserRole.CUSTOMER)) {
                throw new OrderException(BUYER_IS_PLATFORM_USER);
            }
            //判断买家状态
            if (userInfo.getState() != null && !userInfo.getState().equals(UserStateEnum.ACTIVE.name())) {
                throw new OrderException(BUYER_STATUS_ABNORMAL);
            }
            //判断买家状态
            if (userInfo.getState() != null && !userInfo.getCertification()) {
                throw new OrderException(BUYER_NOT_AUTH);
            }
        }
    }

    public UserValidator(UserFacadeService userFacadeService) {
        this.userFacadeService = userFacadeService;
    }

    public UserValidator() {
    }
}
