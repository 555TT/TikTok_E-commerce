package cn.whr.nft.turbo.api.goods.request;

import cn.whr.nft.turbo.api.goods.constant.GoodsEvent;

public record GoodsCancelSaleRequest(String identifier, Long collectionId, Integer quantity) {

    public GoodsEvent eventType() {
        return GoodsEvent.CANCEL_SALE;
    }
}
