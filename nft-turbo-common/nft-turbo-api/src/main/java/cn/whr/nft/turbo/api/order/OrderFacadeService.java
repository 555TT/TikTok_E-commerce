package cn.whr.nft.turbo.api.order;

import cn.whr.nft.turbo.api.order.model.TradeOrderVO;
import cn.whr.nft.turbo.api.order.request.*;
import cn.whr.nft.turbo.api.order.request.OrderCreateRequest;
import cn.whr.nft.turbo.api.order.request.OrderPayRequest;
import cn.whr.nft.turbo.api.order.response.OrderResponse;
import cn.whr.nft.turbo.base.response.PageResponse;
import cn.whr.nft.turbo.base.response.SingleResponse;

/**
 * @author whr
 */
public interface OrderFacadeService {

    /**
     * 创建订单
     *
     * @param request
     * @return
     */
    public OrderResponse create(OrderCreateRequest request);

    /**
     * 取消订单
     *
     * @param request
     * @return
     */
    public OrderResponse cancel(OrderCancelRequest request);

    /**
     * 订单超时
     *
     * @param request
     * @return
     */
    public OrderResponse timeout(OrderTimeoutRequest request);

    /**
     * 订单确认
     *
     * @param request
     * @return
     */
    public OrderResponse confirm(OrderConfirmRequest request);

    /**
     * 创建并确认订单
     * @param request
     * @return
     */
    public OrderResponse createAndConfirm(OrderCreateAndConfirmRequest request);

    /**
     * 订单支付成功
     *
     * @param request
     * @return
     */
    public OrderResponse paySuccess(OrderPayRequest request);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId);

    /**
     * 订单详情
     *
     * @param orderId
     * @param userId
     * @return
     */
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId, String userId);

    /**
     * 订单分页查询
     *
     * @param request
     * @return
     */
    public PageResponse<TradeOrderVO> pageQuery(OrderPageQueryRequest request);
}
