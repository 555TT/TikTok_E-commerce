package cn.whr.nft.turbo.order.domain.listener;

import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.whr.nft.turbo.api.user.constant.UserType;
import cn.whr.nft.turbo.order.domain.entity.TradeOrder;
import cn.whr.nft.turbo.order.domain.listener.event.OrderCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

/**
 * @author whr
 */
@Component
public class OrderEventListener {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @TransactionalEventListener(value = OrderCreateEvent.class)
    @Async("orderListenExecutor")
    public void onApplicationEvent(OrderCreateEvent event) {

        TradeOrder tradeOrder = (TradeOrder) event.getSource();
        OrderConfirmRequest confirmRequest = new OrderConfirmRequest();
        confirmRequest.setOperator(UserType.PLATFORM.name());
        confirmRequest.setOperatorType(UserType.PLATFORM);
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setIdentifier(tradeOrder.getIdentifier());
        confirmRequest.setOperateTime(new Date());
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setBuyerId(tradeOrder.getBuyerId());
        confirmRequest.setItemCount(tradeOrder.getItemCount());
        confirmRequest.setGoodsType(tradeOrder.getGoodsType());
        confirmRequest.setGoodsId(tradeOrder.getGoodsId());
        orderFacadeService.confirm(confirmRequest);
    }
}
