package cn.whr.nft.turbo.order.domain.listener.event;

import cn.whr.nft.turbo.order.domain.entity.TradeOrder;
import org.springframework.context.ApplicationEvent;

/**
 * @author whr
 */
public class OrderCreateEvent extends ApplicationEvent {

    public OrderCreateEvent(TradeOrder tradeOrder) {
        super(tradeOrder);
    }
}
