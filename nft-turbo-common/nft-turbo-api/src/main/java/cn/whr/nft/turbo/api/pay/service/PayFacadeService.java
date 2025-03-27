package cn.whr.nft.turbo.api.pay.service;

import cn.whr.nft.turbo.api.pay.model.PayOrderVO;
import cn.whr.nft.turbo.api.pay.request.PayCreateRequest;
import cn.whr.nft.turbo.api.pay.request.PayQueryRequest;
import cn.whr.nft.turbo.api.pay.response.PayCreateResponse;
import cn.whr.nft.turbo.api.pay.response.PayQueryResponse;
import cn.whr.nft.turbo.base.response.MultiResponse;
import cn.whr.nft.turbo.base.response.SingleResponse;

/**
 * @author whr
 */
public interface PayFacadeService {

    /**
     * 生成支付链接
     *
     * @param payCreateRequest
     * @return
     */
    public PayCreateResponse generatePayUrl(PayCreateRequest payCreateRequest);

    /**
     * 查询支付订单
     *
     * @param payQueryRequest
     * @return
     */
    public MultiResponse<PayOrderVO> queryPayOrders(PayQueryRequest payQueryRequest);

    /**
     * 查询支付订单
     *
     * @param payOrderId
     * @return
     */
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId);

    /**
     * 查询支付订单
     *
     * @param payOrderId
     * @param payerId
     * @return
     */
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId, String payerId);


}
