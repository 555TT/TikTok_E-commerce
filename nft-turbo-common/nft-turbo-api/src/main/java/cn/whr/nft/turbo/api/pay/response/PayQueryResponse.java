package cn.whr.nft.turbo.api.pay.response;

import cn.whr.nft.turbo.api.pay.model.PayOrderVO;
import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author whr
 */
@Getter
@Setter
public class PayQueryResponse extends BaseResponse {

    private List<PayOrderVO> payOrders;
}
