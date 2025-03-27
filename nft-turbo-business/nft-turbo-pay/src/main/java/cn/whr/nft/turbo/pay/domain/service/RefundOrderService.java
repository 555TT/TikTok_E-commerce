package cn.whr.nft.turbo.pay.domain.service;

import cn.whr.nft.turbo.api.pay.constant.PayRefundOrderState;
import cn.whr.nft.turbo.api.pay.request.RefundCreateRequest;
import cn.whr.nft.turbo.base.exception.BizException;
import cn.whr.nft.turbo.base.exception.RepoErrorCode;
import cn.whr.nft.turbo.pay.domain.entity.PayOrder;
import cn.whr.nft.turbo.pay.domain.entity.RefundOrder;
import cn.whr.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.whr.nft.turbo.pay.infrastructure.mapper.PayOrderMapper;
import cn.whr.nft.turbo.pay.infrastructure.mapper.RefundOrderMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author whr
 */
@Service
public class RefundOrderService extends ServiceImpl<RefundOrderMapper, RefundOrder> {
    private static final Logger logger = LoggerFactory.getLogger(PayOrderService.class);

    @Autowired
    private RefundOrderMapper refundOrderMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public RefundOrderMapper getBaseMapper() {
        return this.refundOrderMapper;
    }

    public RefundOrder queryByOrderId(String refundOrderId) {
        QueryWrapper<RefundOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("refund_order_id", refundOrderId);
        return this.getOne(queryWrapper);
    }

    public RefundOrder create(RefundCreateRequest refundCreateRequest) {
        RefundOrder existRefundOrder = refundOrderMapper.selectByIdentifier(refundCreateRequest.getPayOrderId(), refundCreateRequest.getIdentifier(), refundCreateRequest.getRefundChannel().name());

        if (existRefundOrder != null) {
            return existRefundOrder;
        }

        PayOrder payOrder = payOrderMapper.selectByPayOrderId(refundCreateRequest.getPayOrderId());

        RefundOrder refundOrder = RefundOrder.create(refundCreateRequest, payOrder);
        boolean saveResult = save(refundOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.INSERT_FAILED));

        return refundOrder;
    }

    public boolean refunding(String refundOrderId) {
        RefundOrder refundOrder = refundOrderMapper.selectByRefundOrderId(refundOrderId);
        refundOrder.refunding();

        boolean saveResult = saveOrUpdate(refundOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public boolean refundSuccess(RefundSuccessEvent refundSuccessEvent) {
        RefundOrder refundOrder = refundOrderMapper.selectByRefundOrderId(refundSuccessEvent.getRefundOrderId());
        refundOrder.refundSuccess(refundSuccessEvent);

        boolean saveResult = saveOrUpdate(refundOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public Page<RefundOrder> pageQueryNeedRetryOrders(int currentPage, int pageSize) {
        Page<RefundOrder> page = new Page<>(currentPage, pageSize);

        QueryWrapper<RefundOrder> wrapper = new QueryWrapper<>();
        wrapper.in("refund_order_state", PayRefundOrderState.REFUNDING, PayRefundOrderState.TO_REFUND);
        wrapper.orderBy(true, true, "gmt_create");

        return this.page(page, wrapper);
    }
}
