package cn.whr.nft.turbo.goods.domain.service;

import cn.whr.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.whr.nft.turbo.api.collection.request.CollectionAirDropRequest;
import cn.whr.nft.turbo.api.collection.request.CollectionCreateRequest;
import cn.whr.nft.turbo.api.collection.response.CollectionAirdropResponse;
import cn.whr.nft.turbo.api.goods.request.GoodsConfirmSaleRequest;
import cn.whr.nft.turbo.api.goods.request.GoodsTrySaleRequest;
import cn.whr.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.whr.nft.turbo.collection.domain.entity.Collection;
import cn.whr.nft.turbo.collection.domain.service.CollectionService;
import cn.whr.nft.turbo.goods.GoodsBaseTest;
import io.seata.common.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class CollectionServiceTest extends GoodsBaseTest {

    @Autowired
    private CollectionService collectionService;

    @Test
    public void createTest() {
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
        var queRes = collectionService.queryById(collection.getId());
        Assert.assertTrue(queRes.getId() != null);

    }


    @Test
    public void airDropTest() {
        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier("123456789");
        request.setName("name");
        request.setCover("cover");
        request.setPrice(BigDecimal.ONE);
        request.setQuantity(100L);
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        Collection collection = collectionService.create(request);
        Assert.assertTrue(collection.getId() != null);
        CollectionAirDropRequest airDropRequest = new CollectionAirDropRequest();
        airDropRequest.setIdentifier("12345679");
        airDropRequest.setCollectionId(collection.getId());
        airDropRequest.setQuantity(1);
        airDropRequest.setRecipientUserId("1234567");
        airDropRequest.setBizType(GoodsSaleBizType.AIR_DROP);
        CollectionAirdropResponse response = collectionService.airDrop(airDropRequest,collection);
        Assert.assertTrue(StringUtils.equals(response.getHeldCollections().get(0).getUserId(), "1234567"));

    }


    @Test
    public void saleTest() {
        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier("1234567");
        request.setName("name");
        request.setCover("cover");
        request.setPrice(BigDecimal.ONE);
        request.setQuantity(100L);
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        Collection collection = collectionService.create(request);
        Assert.assertTrue(collection.getId() != null);
        GoodsTrySaleRequest collectionTrySaleRequest = new GoodsTrySaleRequest("test123", collection.getId(), 1);
        boolean tryRes = collectionService.trySale(collectionTrySaleRequest);
        Assert.assertTrue(tryRes);
        var queRes = collectionService.queryById(collection.getId());
        Assert.assertTrue(queRes.getSaleableInventory() == 99L);
        GoodsConfirmSaleRequest collectionSaleConfirm = new GoodsConfirmSaleRequest("676776", collection.getId(), 1, "23123", GoodsSaleBizType.PRIMARY_TRADE.name(), "321321", "name", "cover", BigDecimal.ONE);
        GoodsSaleResponse confirmRes = collectionService.confirmSale(collectionSaleConfirm);

    }
}
