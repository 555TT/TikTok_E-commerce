package cn.whr.nft.turbo.admin.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 藏品下架参数
 *
 * @author wangyibo
 */
@Setter
@Getter
public class AdminCollectionRemoveParam {

    /**
     * '藏品id'
     */
    @NotNull(message = "藏品id不能为空")
    private Long collectionId;
}
