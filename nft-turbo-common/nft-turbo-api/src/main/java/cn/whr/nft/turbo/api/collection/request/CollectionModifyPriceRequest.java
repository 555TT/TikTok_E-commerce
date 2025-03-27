package cn.whr.nft.turbo.api.collection.request;

import java.math.BigDecimal;

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
public class CollectionModifyPriceRequest extends BaseCollectionRequest {

    /**
     * '价格'
     */
    private BigDecimal price;


    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.MODIFY_PRICE;
    }
}
