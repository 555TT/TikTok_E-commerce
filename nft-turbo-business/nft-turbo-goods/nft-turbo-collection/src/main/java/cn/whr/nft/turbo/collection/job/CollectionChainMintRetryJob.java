package cn.whr.nft.turbo.collection.job;

import cn.whr.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.whr.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.user.request.UserQueryRequest;
import cn.whr.nft.turbo.api.user.response.UserQueryResponse;
import cn.whr.nft.turbo.api.user.response.data.UserInfo;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.base.utils.RemoteCallWrapper;
import cn.whr.nft.turbo.collection.domain.entity.HeldCollection;
import cn.whr.nft.turbo.collection.domain.service.CollectionService;
import cn.whr.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 藏品上链铸造重试任务
 *
 * @author whr
 */
@Component
public class CollectionChainMintRetryJob {

    @Autowired
    private HeldCollectionService heldCollectionService;

    @Autowired
    private ChainFacadeService chainFacadeService;

    @Autowired
    private UserFacadeService userFacadeService;

    private static final int PAGE_SIZE = 100;

    private static final Logger LOG = LoggerFactory.getLogger(CollectionChainMintRetryJob.class);

    @XxlJob("collectionChainMintRetryJob")
    public ReturnT<String> execute() {

        int currentPage = 1;
        Page<HeldCollection> page = heldCollectionService.pageQueryForChainMint(currentPage, PAGE_SIZE);

        page.getRecords().forEach(this::executeSingle);

        while (page.hasNext()) {
            currentPage++;
            page = heldCollectionService.pageQueryForChainMint(currentPage, PAGE_SIZE);
            page.getRecords().forEach(this::executeSingle);
        }

        return ReturnT.SUCCESS;
    }

    private void executeSingle(HeldCollection heldCollection) {
        LOG.info("start to execute chainMint retry , heldCollectionId is {}", heldCollection.getId());

        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(heldCollection.getUserId()));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);

        ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
        chainProcessRequest.setRecipient(userQueryResponse.getData().getBlockChainUrl());
        chainProcessRequest.setClassId(heldCollection.getCollectionId().toString());
        chainProcessRequest.setClassName(heldCollection.getName());
        chainProcessRequest.setSerialNo(heldCollection.getSerialNo());
        chainProcessRequest.setBizId(heldCollection.getId().toString());
        chainProcessRequest.setBizType(ChainOperateBizTypeEnum.HELD_COLLECTION.name());
        chainProcessRequest.setIdentifier(heldCollection.getId().toString());

        //如果失败了，则依靠定时任务补偿
        RemoteCallWrapper.call(req -> chainFacadeService.mint(req), chainProcessRequest, "mint");
        LOG.info("transaction is commit ,end to mint , heldCollectionId : " + heldCollection.getId());
    }
}
