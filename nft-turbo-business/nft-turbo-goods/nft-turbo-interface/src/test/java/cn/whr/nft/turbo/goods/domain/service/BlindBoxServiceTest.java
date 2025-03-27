package cn.whr.nft.turbo.goods.domain.service;

import cn.whr.nft.turbo.api.box.constant.BlindAllotBoxRule;
import cn.whr.nft.turbo.api.box.constant.BlindBoxStateEnum;
import cn.whr.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.whr.nft.turbo.api.box.request.BlindBoxItemCreateRequest;
import cn.whr.nft.turbo.api.box.response.BlindBoxCreateResponse;
import cn.whr.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.whr.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.whr.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.whr.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.whr.nft.turbo.api.goods.request.GoodsConfirmSaleRequest;
import cn.whr.nft.turbo.api.goods.request.GoodsTrySaleRequest;
import cn.whr.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.whr.nft.turbo.box.domain.entity.BlindBox;
import cn.whr.nft.turbo.box.domain.service.BlindBoxService;
import cn.whr.nft.turbo.goods.GoodsBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BlindBoxServiceTest extends GoodsBaseTest {

    @Autowired
    private BlindBoxManageFacadeService blindBoxManageFacadeService;
    @Autowired
    private BlindBoxService blindBoxService;


    @Test
    public void saleTest() {
        BlindBoxCreateRequest request = new BlindBoxCreateRequest();
        request.setName("blindName");
        request.setQuantity(10L);
        request.setCover("blindCover");
        request.setDetail("blindDetail");
        request.setPrice(BigDecimal.TEN);
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCreatorId("123456");
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        List<BlindBoxItemCreateRequest> boxItemCreateRequests = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            BlindBoxItemCreateRequest blindBoxItemCreateRequest = new BlindBoxItemCreateRequest();
            blindBoxItemCreateRequest.setCollectionCover("cover" + i);
            blindBoxItemCreateRequest.setQuantity(5L);
            blindBoxItemCreateRequest.setCollectionDetail("detail" + i);
            boxItemCreateRequests.add(blindBoxItemCreateRequest);
        }
        request.setBlindBoxItemCreateRequests(boxItemCreateRequests);
        request.setAllocateRule(BlindAllotBoxRule.RANDOM.name());
        ChainProcessResponse<ChainOperationData> chainProcessResponse = new ChainProcessResponse<>();
        chainProcessResponse.setSuccess(true);
        when(chainFacadeService.chain(any())).thenReturn(chainProcessResponse);
        BlindBoxCreateResponse response = blindBoxManageFacadeService.create(request);
        Assert.assertTrue(response.getSuccess());
        BlindBox blindBox = blindBoxService.getById(response.getBlindBoxId());
        Assert.assertEquals(blindBox.getName(), "blindName");

        blindBox.setState(BlindBoxStateEnum.SUCCEED);
        blindBox.setSyncChainTime(new Date());
        blindBoxService.updateById(blindBox);


        GoodsTrySaleRequest collectionTrySaleRequest = new GoodsTrySaleRequest("test123", blindBox.getId(), 1);
        boolean tryRes = blindBoxService.trySale(collectionTrySaleRequest);
        Assert.assertTrue(tryRes);
        var queRes = blindBoxService.getById(blindBox.getId());
        Assert.assertTrue(queRes.getSaleableInventory() == 9L);
        GoodsConfirmSaleRequest collectionSaleConfirm = new GoodsConfirmSaleRequest("676776", blindBox.getId(), 1, "23123", GoodsSaleBizType.BLIND_BOX_TRADE.name(), "321321", "name", "cover", BigDecimal.ONE);
        GoodsSaleResponse confirmRes = blindBoxService.confirmSale(collectionSaleConfirm);
        queRes = blindBoxService.getById(blindBox.getId());
        Assert.assertTrue(queRes.getOccupiedInventory() == 1L);
    }
}
