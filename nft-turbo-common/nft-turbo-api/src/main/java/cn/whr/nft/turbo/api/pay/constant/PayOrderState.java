package cn.whr.nft.turbo.api.pay.constant;

/**
 * @author whr
 */
public enum PayOrderState {

    /**
     * 待支付
     */
    TO_PAY,

    /**
     * 支付中
     */
    PAYING,

    /**
     * 已付款
     */
    PAID,

    /**
     * 支付失败
     */
    FAILED,

    /**
     * 支付超时
     */
    EXPIRED,

    /**
     * 已退款
     */
    REFUNDED;
}
