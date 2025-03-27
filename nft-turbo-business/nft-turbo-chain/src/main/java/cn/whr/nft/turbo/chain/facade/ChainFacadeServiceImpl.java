package cn.whr.nft.turbo.chain.facade;

import cn.whr.nft.turbo.api.chain.constant.ChainType;
import cn.whr.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.whr.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.whr.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.whr.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.chain.domain.service.ChainService;
import cn.whr.nft.turbo.chain.domain.service.ChainServiceFactory;
import cn.whr.nft.turbo.rpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static cn.whr.nft.turbo.base.constant.ProfileConstant.PROFILE_DEV;

/**
 * @author whr
 */
@DubboService(version = "1.0.0")
public class ChainFacadeServiceImpl implements ChainFacadeService {

    @Value("${nft.turbo.chain.type:MOCK}")
    private String chainType;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private ChainServiceFactory chainServiceFactory;

    @Override
    @Facade
    public ChainProcessResponse<ChainCreateData> createAddr(ChainProcessRequest request) {
        return getChainService().createAddr(request);
    }

    @Override
    @Facade
    public ChainProcessResponse<ChainOperationData> chain(ChainProcessRequest request) {
        return getChainService().chain(request);
    }

    @Override
    @Facade
    public ChainProcessResponse<ChainOperationData> mint(ChainProcessRequest request) {
        return getChainService().mint(request);
    }

    @Override
    @Facade
    public ChainProcessResponse<ChainOperationData> transfer(ChainProcessRequest request) {
        return getChainService().transfer(request);
    }

    @Override
    @Facade
    public ChainProcessResponse<ChainOperationData> destroy(ChainProcessRequest request) {
        return getChainService().destroy(request);
    }

    private ChainService getChainService() {
        if (PROFILE_DEV.equals(profile)) {
            return chainServiceFactory.get(ChainType.MOCK);
        }

        ChainService chainService = chainServiceFactory.get(ChainType.valueOf(chainType));
        return chainService;
    }
}
