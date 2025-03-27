package cn.whr.nft.turbo.api.box.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Getter
@Setter
public class BlindBoxCreateResponse extends BaseResponse {
    /**
     * 盲盒id
     */
    private Long blindBoxId;

}
