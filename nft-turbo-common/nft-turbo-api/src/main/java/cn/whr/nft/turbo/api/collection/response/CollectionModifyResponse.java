package cn.whr.nft.turbo.api.collection.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Getter
@Setter
public class CollectionModifyResponse extends BaseResponse {
    /**
     * 藏品id
     */
    private Long collectionId;
}
