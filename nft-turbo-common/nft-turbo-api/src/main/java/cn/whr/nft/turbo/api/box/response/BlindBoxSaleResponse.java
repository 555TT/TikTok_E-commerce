package cn.whr.nft.turbo.api.box.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Getter
@Setter
public class BlindBoxSaleResponse extends BaseResponse {
    /**
     * 盲盒条目id
     */
    private Long blindBoxItemId;

}
