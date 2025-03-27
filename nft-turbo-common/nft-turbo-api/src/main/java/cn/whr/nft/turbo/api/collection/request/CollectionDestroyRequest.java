package cn.whr.nft.turbo.api.collection.request;

import cn.whr.nft.turbo.api.goods.constant.GoodsEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wswyb001
 * @date 2024/01/17
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDestroyRequest extends BaseCollectionRequest {
    /**
     * '持有藏品id'
     */
    private Long heldCollectionId;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.DESTROY;
    }
}
