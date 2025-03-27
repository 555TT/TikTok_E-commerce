package cn.whr.nft.turbo.api.box.request;

import cn.whr.nft.turbo.api.collection.constant.CollectionRarity;
import cn.whr.nft.turbo.base.request.BaseRequest;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author wswyb001
 * @date 2024/01/17
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlindBoxItemCreateRequest extends BaseRequest {

    /**
     * '藏品名称'
     */
    private String collectionName;

    /**
     * '藏品封面'
     */
    private String collectionCover;

    /**
     * '藏品详情'
     */
    private String collectionDetail;

    /**
     * 参考价格
     */
    private BigDecimal referencePrice;

    /**
     * 稀有度
     */
    private CollectionRarity rarity;

    /**
     * '数量'
     */
    private Long quantity;
}
