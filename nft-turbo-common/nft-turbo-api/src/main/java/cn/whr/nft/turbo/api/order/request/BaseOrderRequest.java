package cn.whr.nft.turbo.api.order.request;

import cn.whr.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.whr.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public abstract class BaseOrderRequest extends BaseRequest {

    /**
     * 操作幂等号
     */
    @NotNull(message = "identifier 不能为空")
    private String identifier;

    /**
     * 获取订单事件
     *
     * @return
     */
    public abstract TradeOrderEvent getOrderEvent();
}
