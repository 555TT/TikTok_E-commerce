package cn.whr.nft.turbo.goods.facade.service;

import cn.whr.nft.turbo.api.box.model.BlindBoxVO;
import cn.whr.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.whr.nft.turbo.api.collection.model.CollectionVO;
import cn.whr.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.whr.nft.turbo.api.goods.constant.GoodsType;
import cn.whr.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.whr.nft.turbo.api.goods.request.*;
import cn.whr.nft.turbo.api.goods.response.GoodsBookResponse;
import cn.whr.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.whr.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.whr.nft.turbo.base.response.SingleResponse;
import cn.whr.nft.turbo.box.domain.request.BlindBoxAssignRequest;
import cn.whr.nft.turbo.box.domain.service.BlindBoxService;
import cn.whr.nft.turbo.collection.domain.entity.HeldCollection;
import cn.whr.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.whr.nft.turbo.collection.domain.service.CollectionService;
import cn.whr.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.whr.nft.turbo.goods.service.GoodsBookService;
import cn.whr.nft.turbo.goods.service.HotGoodsService;
import cn.whr.nft.turbo.rpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品聚合服务
 *
 * @author whr
 */
@DubboService(version = "1.0.0")
public class GoodsFacadeServiceImpl implements GoodsFacadeService {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private BlindBoxService blindBoxService;

    @Autowired
    private CollectionReadFacadeService collectionReadFacadeService;

    @Autowired
    private BlindBoxReadFacadeService blindBoxReadFacadeService;

    @Autowired
    private GoodsBookService goodsBookService;

    @Autowired
    private HotGoodsService hotGoodsService;

    @Autowired
    private HeldCollectionService heldCollectionService;

    @Override
    public BaseGoodsVO getGoods(String goodsId, GoodsType goodsType) {
        return switch (goodsType) {
            case COLLECTION -> {
                SingleResponse<CollectionVO> response = collectionReadFacadeService.queryById(Long.valueOf(goodsId));
                if (response.getSuccess()) {
                    yield response.getData();
                }
                yield null;
            }

            case BLIND_BOX -> {
                SingleResponse<BlindBoxVO> response = blindBoxReadFacadeService.queryById(Long.valueOf(goodsId));
                if (response.getSuccess()) {
                    yield response.getData();
                }
                yield null;
            }
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };
    }

    @Override
    public GoodsSaleResponse trySale(GoodsSaleRequest request) {
        GoodsTrySaleRequest goodsTrySaleRequest = new GoodsTrySaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());
        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean trySaleResult = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.trySale(goodsTrySaleRequest);
            case COLLECTION -> collectionService.trySale(goodsTrySaleRequest);
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(trySaleResult);
        return response;
    }

    @Override
    public GoodsSaleResponse trySaleWithoutHint(GoodsSaleRequest request) {
        GoodsTrySaleRequest collectionTrySaleRequest = new GoodsTrySaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean trySaleResult = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.trySaleWithoutHint(collectionTrySaleRequest);
            case COLLECTION -> collectionService.trySaleWithoutHint(collectionTrySaleRequest);
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(trySaleResult);
        return response;
    }


    @Override
    @Deprecated
    public GoodsSaleResponse confirmSale(GoodsSaleRequest request) {
        GoodsConfirmSaleRequest confirmSaleRequest = new GoodsConfirmSaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity(), request.getBizNo(), request.getBizType(), request.getUserId(), request.getName(), request.getCover(), request.getPurchasePrice());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        return switch (goodsType) {
            case BLIND_BOX -> blindBoxService.confirmSale(confirmSaleRequest);
            case COLLECTION -> collectionService.confirmSale(confirmSaleRequest);
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };
    }

    @Override
    public GoodsSaleResponse paySuccess(GoodsSaleRequest request) {
        GoodsSaleResponse response = new GoodsSaleResponse();
        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        return switch (goodsType) {
            case BLIND_BOX -> {
                BlindBoxAssignRequest blindBoxAssignRequest = new BlindBoxAssignRequest();
                blindBoxAssignRequest.setBlindBoxId(request.getGoodsId());
                blindBoxAssignRequest.setUserId(request.getUserId());
                blindBoxAssignRequest.setOrderId(request.getBizNo());
                blindBoxService.assign(blindBoxAssignRequest);
                response.setSuccess(true);
                yield response;
            }
            case COLLECTION -> {
                HeldCollectionCreateRequest heldCollectionCreateRequest = new HeldCollectionCreateRequest();
                BeanUtils.copyProperties(request, heldCollectionCreateRequest);
                heldCollectionCreateRequest.setReferencePrice(request.getPurchasePrice());
                heldCollectionCreateRequest.setSerialNoBaseId(request.getGoodsId().toString());

                HeldCollection heldCollection = heldCollectionService.create(heldCollectionCreateRequest);
                response.setSuccess(true);
                response.setHeldCollectionId(heldCollection.getId());
                yield response;
            }
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };
    }

    @Override
    public GoodsSaleResponse cancelSale(GoodsSaleRequest request) {
        GoodsCancelSaleRequest goodsCancelSaleRequest = new GoodsCancelSaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean result = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.cancelSale(goodsCancelSaleRequest);
            case COLLECTION -> collectionService.cancelSale(goodsCancelSaleRequest);
            default -> throw new UnsupportedOperationException("unsupport goods type");
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(result);
        return response;
    }

    @Override
    @Facade
    public GoodsBookResponse book(GoodsBookRequest request) {
        BaseGoodsVO goodsVO = this.getGoods(request.getGoodsId(), request.getGoodsType());
        if (goodsVO.canBookNow()) {
            return goodsBookService.book(request);
        }
        throw new RuntimeException("GOODS_CAN_NOT_BOOK_NOW");
    }

    @Override
    @Facade
    public Boolean isGoodsBooked(String goodsId, GoodsType goodsType, String buyerId) {
        return goodsBookService.isBooked(goodsId, goodsType, buyerId);
    }

    @Override
    @Facade
    public Boolean addHotGoods(String goodsId, String goodsType) {
        hotGoodsService.addHotGoods(goodsId, goodsType);
        //不抛异常就视为成功
        return true;
    }

    @Override
    @Facade
    public Boolean isHotGoods(String goodsId, String goodsType) {
        return hotGoodsService.isHotGoods(goodsId, goodsType);
    }
}
