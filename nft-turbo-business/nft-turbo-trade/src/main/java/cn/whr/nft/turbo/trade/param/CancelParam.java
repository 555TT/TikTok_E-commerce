package cn.whr.nft.turbo.trade.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public class CancelParam {

    @NotNull(message = "orderId is null")
    private String orderId;
}
