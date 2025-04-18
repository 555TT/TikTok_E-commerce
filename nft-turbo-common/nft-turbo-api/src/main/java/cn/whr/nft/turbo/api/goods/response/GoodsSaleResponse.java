package cn.whr.nft.turbo.api.goods.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Getter
@Setter
public class GoodsSaleResponse extends BaseResponse {
    /**
     * 持有藏品id
     */
    private Long heldCollectionId;

}
