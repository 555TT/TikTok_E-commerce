package cn.whr.nft.turbo.collection.domain.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import cn.whr.nft.turbo.collection.domain.entity.Collection;
import cn.whr.nft.turbo.collection.domain.entity.HeldCollection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Setter
@Getter
public class CollectionConfirmSaleResponse extends BaseResponse {

    private Collection collection;

    private HeldCollection heldCollection;
}
