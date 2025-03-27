package cn.whr.nft.turbo.trade.listener;

import cn.whr.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.whr.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.whr.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.whr.nft.turbo.api.inventory.request.InventoryRequest;
import cn.whr.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.whr.nft.turbo.api.order.constant.TradeOrderState;
import cn.whr.nft.turbo.api.order.model.TradeOrderVO;
import cn.whr.nft.turbo.api.order.request.BaseOrderUpdateRequest;
import cn.whr.nft.turbo.api.order.request.OrderCancelRequest;
import cn.whr.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.whr.nft.turbo.base.response.SingleResponse;
import cn.whr.nft.turbo.trade.exception.TradeException;
import cn.whr.turbo.stream.param.MessageBody;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static cn.whr.nft.turbo.trade.exception.TradeErrorCode.INVENTORY_ROLLBACK_FAILED;

/**
 * 交易订单监听器
 *
 * @author whr
 */
@Slf4j
@Component
public class TradeOrderListener {

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Bean
    Consumer<Message<MessageBody>> orderClose() {
        return msg -> {
            String messageId = msg.getHeaders().get("ROCKET_MQ_MESSAGE_ID", String.class);
            String closeType = msg.getHeaders().get("CLOSE_TYPE", String.class);

            BaseOrderUpdateRequest orderUpdateRequest;
            if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
                orderUpdateRequest = JSON.parseObject(msg.getPayload().getBody(), OrderCancelRequest.class);
            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                orderUpdateRequest = JSON.parseObject(msg.getPayload().getBody(), OrderTimeoutRequest.class);
            } else {
                throw new UnsupportedOperationException("unsupported closeType " + closeType);
            }

            log.info("Received OrderClose Message  messageId:{},orderCloseRequest:{}，closeType:{}", messageId, JSON.toJSONString(orderUpdateRequest), closeType);

            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderUpdateRequest.getOrderId());
            if (!response.getSuccess()) {
                log.error("getTradeOrder failed,orderCloseRequest:{} , orderQueryResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(response));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
            TradeOrderVO tradeOrderVO = response.getData();
            if (response.getData().getOrderState() != TradeOrderState.CLOSED) {
                log.error("trade order state is illegal ,orderCloseRequest:{} , tradeOrderVO : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(tradeOrderVO));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(tradeOrderVO);
            GoodsSaleResponse cancelSaleResult = goodsFacadeService.cancelSale(goodsSaleRequest);
            if (!cancelSaleResult.getSuccess()) {
                log.error("cancelSale failed,orderCloseRequest:{} , collectionSaleResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(cancelSaleResult));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            InventoryRequest collectionInventoryRequest = new InventoryRequest(tradeOrderVO);
            SingleResponse<Boolean> decreaseResponse = inventoryFacadeService.increase(collectionInventoryRequest);
            if (decreaseResponse.getSuccess()) {
                log.info("increase success,collectionInventoryRequest:{}", collectionInventoryRequest);
            } else {
                log.error("increase inventory failed,orderCloseRequest:{} , decreaseResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(decreaseResponse));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
        };
    }
}
