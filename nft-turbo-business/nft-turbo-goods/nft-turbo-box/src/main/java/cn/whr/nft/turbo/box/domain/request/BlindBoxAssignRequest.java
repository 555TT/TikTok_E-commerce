package cn.whr.nft.turbo.box.domain.request;

import cn.whr.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * @author whr
 * 盲盒分配入参
 * @date 2025/01/11
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlindBoxAssignRequest extends BaseRequest {

    /**
     * '盲盒id'
     */
    private Long blindBoxId;

    /**
     * '用户id'
     */
    private String userId;

    /**
     * '订单id'
     */
    private String orderId;
}