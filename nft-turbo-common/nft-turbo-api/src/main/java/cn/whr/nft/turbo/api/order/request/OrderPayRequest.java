package cn.whr.nft.turbo.api.order.request;

import cn.whr.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.whr.nft.turbo.api.pay.constant.PayChannel;
import cn.whr.nft.turbo.api.pay.model.PayOrderVO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author whr
 */
@Getter
@Setter
public class OrderPayRequest extends BaseOrderUpdateRequest {

    /**
     * 支付方式
     */
    private PayChannel payChannel;

    /**
     * 支付流水号
     */
    private String payStreamId;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.PAY;
    }
}

