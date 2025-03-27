package cn.whr.nft.turbo.api.pay.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public class PayCreateResponse extends BaseResponse {

    private String payOrderId;

    private String payUrl;
}
