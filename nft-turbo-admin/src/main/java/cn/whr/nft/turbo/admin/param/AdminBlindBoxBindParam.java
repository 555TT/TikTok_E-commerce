package cn.whr.nft.turbo.admin.param;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 盲盒创建参数
 *
 * @author wangyibo
 */
@Setter
@Getter
public class AdminBlindBoxBindParam {

    /**
     * 普通藏品
     */
    private List<Long> heldCollectionIds;

    /**
     * 稀有藏品
     */
    private List<Long> rareHeldCollectionIds;


}
