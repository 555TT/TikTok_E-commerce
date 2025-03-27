package cn.whr.nft.turbo.goods.domain.service;

import cn.whr.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.whr.nft.turbo.api.collection.constant.HeldCollectionState;
import cn.whr.nft.turbo.api.collection.request.CollectionCreateRequest;
import cn.whr.nft.turbo.collection.domain.entity.Collection;
import cn.whr.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.whr.nft.turbo.collection.domain.request.HeldCollectionDestroyRequest;
import cn.whr.nft.turbo.collection.domain.request.HeldCollectionTransferRequest;
import cn.whr.nft.turbo.collection.domain.service.CollectionService;
import cn.whr.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.whr.nft.turbo.goods.GoodsBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class HeldCollectionServiceTest extends GoodsBaseTest {
    @Autowired
    private HeldCollectionService heldCollectionService;

    @Autowired
    private CollectionService collectionService;

    @Test
    public void serviceTest() {
        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier("123456");
        request.setName("name");
        request.setCover("cover");
        request.setPrice(BigDecimal.ONE);
        request.setQuantity(100L);
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        Collection collection = collectionService.create(request);
        Assert.assertTrue(collection.getId() != null);

        //create
        HeldCollectionCreateRequest mintRequest = new HeldCollectionCreateRequest();
        mintRequest.setGoodsId(collection.getId());
        mintRequest.setIdentifier("123");
        mintRequest.setSerialNo("12345");
        mintRequest.setBizType(GoodsSaleBizType.PRIMARY_TRADE.name());
        mintRequest.setUserId("1");
        var heldCollection = heldCollectionService.create(mintRequest);
        Assert.assertTrue(heldCollection.getId() != null);
        Assert.assertTrue(heldCollection.getState() == HeldCollectionState.INIT);
        //transfer
        HeldCollectionTransferRequest transferRequest = new HeldCollectionTransferRequest();
        transferRequest.setHeldCollectionId(heldCollection.getId().toString());
        transferRequest.setIdentifier("345");
        transferRequest.setRecipientUserId("2");
        transferRequest.setOperatorId("1");
        var newHeldCollection = heldCollectionService.transfer(transferRequest);
        Assert.assertTrue(newHeldCollection.getId() != null);
        Assert.assertTrue(newHeldCollection.getState() == HeldCollectionState.INIT);
        var oldHeldCollection = heldCollectionService.queryByCollectionIdAndSerialNo(heldCollection.getCollectionId(),
                heldCollection.getSerialNo());
        Assert.assertTrue(oldHeldCollection.getState() == HeldCollectionState.INACTIVED);
        //destroy
        HeldCollectionDestroyRequest destroyRequest = new HeldCollectionDestroyRequest();
        destroyRequest.setHeldCollectionId(newHeldCollection.getId().toString());
        destroyRequest.setIdentifier("456");
        var destroyHeldCollection = heldCollectionService.destroy(destroyRequest);
        Assert.assertTrue(destroyHeldCollection.getId() != null);
        Assert.assertTrue(destroyHeldCollection.getState() == HeldCollectionState.DESTROYED);
    }
}
