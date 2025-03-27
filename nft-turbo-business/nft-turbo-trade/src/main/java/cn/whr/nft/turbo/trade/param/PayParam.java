package cn.whr.nft.turbo.trade.param;

import cn.whr.nft.turbo.api.pay.constant.PayChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public class PayParam {

    @NotNull(message = "orderId is null")
    private String orderId;

    @NotNull(message = "payChannel is null")
    private PayChannel payChannel;

}
