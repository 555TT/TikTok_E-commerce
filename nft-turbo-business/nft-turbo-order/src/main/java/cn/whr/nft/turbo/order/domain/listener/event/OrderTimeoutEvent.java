package cn.whr.nft.turbo.order.domain.listener.event;

import cn.whr.nft.turbo.api.order.request.BaseOrderRequest;
import org.springframework.context.ApplicationEvent;

/**
 * @author whr
 */
public class OrderTimeoutEvent extends ApplicationEvent {

    public OrderTimeoutEvent(BaseOrderRequest baseOrderRequest) {
        super(baseOrderRequest);
    }
}
