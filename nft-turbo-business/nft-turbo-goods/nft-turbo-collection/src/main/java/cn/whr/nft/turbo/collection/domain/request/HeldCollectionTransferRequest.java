package cn.whr.nft.turbo.collection.domain.request;

import cn.whr.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;

/**
 * @author whr
 * @date 2024/01/17
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionTransferRequest extends BaseHeldCollectionRequest {

    /**
     * '买家id'
     */
    private String recipientUserId;

    /**
     * 操作人Id
     */
    private String operatorId;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.TRANSFER;
    }
}
