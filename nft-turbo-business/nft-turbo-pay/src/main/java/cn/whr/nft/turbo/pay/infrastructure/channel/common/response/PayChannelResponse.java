package cn.whr.nft.turbo.pay.infrastructure.channel.common.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Setter
@Getter
public class PayChannelResponse extends BaseResponse {
    protected String payUrl;
}
