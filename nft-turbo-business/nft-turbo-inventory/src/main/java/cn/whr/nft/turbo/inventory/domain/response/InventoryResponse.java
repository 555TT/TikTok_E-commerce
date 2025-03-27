package cn.whr.nft.turbo.inventory.domain.response;

import cn.whr.nft.turbo.api.goods.constant.GoodsType;
import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public class InventoryResponse extends BaseResponse {

    private String goodsId;

    private GoodsType goodsType;

    private String identifier;

    private Integer inventory;
}
