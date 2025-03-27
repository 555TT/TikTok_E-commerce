package cn.whr.nft.turbo.box.domain.listener;

import cn.whr.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.whr.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.whr.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.whr.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.whr.nft.turbo.api.goods.constant.GoodsType;
import cn.whr.nft.turbo.api.user.request.UserQueryRequest;
import cn.whr.nft.turbo.api.user.response.UserQueryResponse;
import cn.whr.nft.turbo.api.user.response.data.UserInfo;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.base.utils.RemoteCallWrapper;
import cn.whr.nft.turbo.box.domain.entity.BlindBoxItem;
import cn.whr.nft.turbo.box.domain.listener.event.BlindBoxOpenEvent;
import cn.whr.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.whr.nft.turbo.box.exception.BlindBoxException;
import cn.whr.nft.turbo.collection.domain.entity.HeldCollection;
import cn.whr.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.whr.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hutool.core.lang.Assert;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static cn.whr.nft.turbo.box.exception.BlindBoxErrorCode.BLIND_BOX_ITEM_SAVE_FAILED;
import static cn.whr.nft.turbo.box.exception.BlindBoxErrorCode.BLIND_BOX_OPEN_FAILED;

/**
 * @author whr
 */
@Component
public class BlindBoxEventListener {

    @Autowired
    private UserFacadeService userFacadeService;

    @Autowired
    private ChainFacadeService chainFacadeService;

    @Autowired
    private BlindBoxItemService blindBoxItemService;

    @Autowired
    private HeldCollectionService heldCollectionService;

    @EventListener(value = BlindBoxOpenEvent.class)
    @Async("blindBoxListenExecutor")
    public void onApplicationEvent(BlindBoxOpenEvent event) {
        BlindBoxItem blindBoxItem = (BlindBoxItem) event.getSource();

        //创建heldCollection
        HeldCollectionCreateRequest heldCollectionCreateRequest = getHeldCollectionCreateRequest(blindBoxItem);
        var heldCollection = heldCollectionService.create(heldCollectionCreateRequest);
        Assert.notNull(heldCollection, () -> new BlindBoxException(BLIND_BOX_OPEN_FAILED));

        //上链
        ChainProcessRequest chainProcessRequest = getChainProcessRequest(blindBoxItem, heldCollection);
        //如果失败了，则依靠定时任务补偿
        ChainProcessResponse<ChainOperationData> response = RemoteCallWrapper.call(req -> chainFacadeService.mint(req), chainProcessRequest, "mint");

        //修改盲盒状态
        if (response.getSuccess()) {
            blindBoxItem.openSuccess();
            var saveResult = blindBoxItemService.updateById(blindBoxItem);
            Assert.isTrue(saveResult, () -> new BlindBoxException(BLIND_BOX_ITEM_SAVE_FAILED));
        }
    }

    private @NotNull ChainProcessRequest getChainProcessRequest(BlindBoxItem blindBoxItem, HeldCollection heldCollection) {
        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(blindBoxItem.getUserId()));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
        chainProcessRequest.setRecipient(userQueryResponse.getData().getBlockChainUrl());
        chainProcessRequest.setClassId(GoodsSaleBizType.BLIND_BOX_TRADE + "_" + blindBoxItem.getBlindBoxId());
        chainProcessRequest.setClassName(blindBoxItem.getName());
        chainProcessRequest.setSerialNo(blindBoxItem.getCollectionSerialNo());
        chainProcessRequest.setBizId(heldCollection.getId().toString());
        chainProcessRequest.setBizType(ChainOperateBizTypeEnum.HELD_COLLECTION.name());
        chainProcessRequest.setIdentifier(blindBoxItem.getId().toString());
        return chainProcessRequest;
    }

    private static @NotNull HeldCollectionCreateRequest getHeldCollectionCreateRequest(BlindBoxItem blindBoxItem) {
        HeldCollectionCreateRequest heldCollectionCreateRequest = new HeldCollectionCreateRequest();
        heldCollectionCreateRequest.setName(blindBoxItem.getCollectionName());
        heldCollectionCreateRequest.setCover(blindBoxItem.getCollectionCover());
        heldCollectionCreateRequest.setBizNo(blindBoxItem.getOrderId());
        heldCollectionCreateRequest.setBizType(GoodsSaleBizType.BLIND_BOX_TRADE.name());
        heldCollectionCreateRequest.setPurchasePrice(blindBoxItem.getPurchasePrice());
        heldCollectionCreateRequest.setReferencePrice(blindBoxItem.getReferencePrice());
        heldCollectionCreateRequest.setRarity(blindBoxItem.getRarity());
        heldCollectionCreateRequest.setUserId(blindBoxItem.getUserId());
        heldCollectionCreateRequest.setGoodsId(blindBoxItem.getId());
        heldCollectionCreateRequest.setGoodsType(GoodsType.BLIND_BOX.name());
        heldCollectionCreateRequest.setSerialNoBaseId(blindBoxItem.getBlindBoxId().toString());
        return heldCollectionCreateRequest;
    }
}
