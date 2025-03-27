package cn.whr.nft.turbo.box.domain.response;

import cn.whr.nft.turbo.base.response.BaseResponse;
import cn.whr.nft.turbo.box.domain.entity.BlindBox;
import cn.whr.nft.turbo.box.domain.entity.BlindBoxItem;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Setter
@Getter
public class BlindBoxConfirmSaleResponse extends BaseResponse {

    private BlindBox blindBox;

    private BlindBoxItem blindBoxItem;
}
