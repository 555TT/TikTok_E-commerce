package cn.whr.nft.turbo.box.facade;

import cn.whr.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.whr.nft.turbo.api.box.response.BlindBoxCreateResponse;
import cn.whr.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.whr.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.whr.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.whr.nft.turbo.box.domain.entity.BlindBox;
import cn.whr.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.whr.nft.turbo.box.domain.service.BlindBoxService;
import cn.whr.nft.turbo.rpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 盲盒管理服务
 *
 * @author whr
 */
@DubboService(version = "1.0.0")
public class BlindBoxManageFacadeServiceImpl implements BlindBoxManageFacadeService {

    @Autowired
    private BlindBoxService blindBoxService;
    @Autowired
    private BlindBoxItemService blindBoxItemService;
    @Autowired
    private ChainFacadeService chainFacadeService;

    @Override
    @Facade
    public BlindBoxCreateResponse create(BlindBoxCreateRequest request) {
        BlindBoxCreateResponse response = new BlindBoxCreateResponse();
        //先创建盲盒
        BlindBox blindBox = blindBoxService.create(request);

        //上链藏品
        ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
        chainProcessRequest.setIdentifier(request.getIdentifier() + "_" + GoodsSaleBizType.BLIND_BOX_TRADE);
        chainProcessRequest.setClassId(GoodsSaleBizType.BLIND_BOX_TRADE + "_" + blindBox.getId());
        chainProcessRequest.setClassName(request.getName());
        chainProcessRequest.setBizType(ChainOperateBizTypeEnum.BLIND_BOX.name());
        chainProcessRequest.setBizId(blindBox.getId().toString());
        var chainRes = chainFacadeService.chain(chainProcessRequest);

        response.setSuccess(chainRes.getSuccess());
        response.setBlindBoxId(blindBox.getId());
        response.setResponseCode(chainRes.getResponseCode());
        response.setResponseMessage(chainRes.getResponseMessage());
        return response;
    }
}
