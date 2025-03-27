package cn.whr.nft.turbo.pay.application.service;

import cn.whr.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.whr.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.whr.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.whr.nft.turbo.api.user.request.UserQueryRequest;
import cn.whr.nft.turbo.api.user.response.UserQueryResponse;
import cn.whr.nft.turbo.api.user.response.data.UserInfo;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.base.response.SingleResponse;
import cn.whr.nft.turbo.base.utils.RemoteCallWrapper;
import cn.whr.nft.turbo.base.utils.SpringContextHolder;
import io.seata.tm.api.transaction.TransactionHook;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付成功事务回调
 *
 * @author whr
 */
@Slf4j
public class PaySuccessTransactionHook implements TransactionHook {

    /**
     * 从 Spring 的上下文中获取到 Bean
     */
    CollectionReadFacadeService collectionFacadeService = (CollectionReadFacadeService) SpringContextHolder.getBean("collectionReadFacadeService");

    UserFacadeService userFacadeService = (UserFacadeService) SpringContextHolder.getBean("userFacadeService");

    ChainFacadeService chainFacadeService = (ChainFacadeService) SpringContextHolder.getBean("chainFacadeService");

    private Long heldCollectionId;

    public PaySuccessTransactionHook() {
    }

    public PaySuccessTransactionHook(Long heldCollectionId) {
        this.heldCollectionId = heldCollectionId;
    }

    @Override
    public void beforeBegin() {
        //do nothing
    }

    @Override
    public void afterBegin() {
        //do nothing
    }

    @Override
    public void beforeCommit() {
        //do nothing
    }

    @Override
    public void afterCommit() {
        log.info("transaction is commit ,start to mint , heldCollectionId : " + heldCollectionId);
        SingleResponse<HeldCollectionVO> response = collectionFacadeService.queryHeldCollectionById(heldCollectionId);

        if (response.getSuccess()) {
            HeldCollectionVO heldCollection = response.getData();
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
            log.info("transaction is commit ,end to mint , heldCollectionId : " + heldCollectionId);
        }
    }

    @Override
    public void beforeRollback() {
        //do nothing
    }

    @Override
    public void afterRollback() {
        log.info("transaction is rollback, do nothing : " + heldCollectionId);
    }

    @Override
    public void afterCompletion() {
        //do nothing
    }
}
