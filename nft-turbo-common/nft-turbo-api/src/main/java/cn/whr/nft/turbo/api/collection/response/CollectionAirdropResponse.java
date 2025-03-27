package cn.whr.nft.turbo.api.collection.response;

import cn.whr.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空投响应
 *
 * @author whr
 */
@Getter
@Setter
public class CollectionAirdropResponse extends BaseResponse {
    /**
     * 持有藏品信息
     */
    private List<HeldCollectionVO> heldCollections;

    /**
     * 空投流水id
     */
    private Long airDropStreamId;
}
