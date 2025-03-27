package cn.whr.nft.turbo.web.util;

import cn.whr.nft.turbo.base.response.PageResponse;
import cn.whr.nft.turbo.web.vo.MultiResult;

import static cn.whr.nft.turbo.base.response.ResponseCode.SUCCESS;

/**
 * @author whr
 */
public class MultiResultConvertor {

    public static <T> MultiResult<T> convert(PageResponse<T> pageResponse) {
        MultiResult<T> multiResult = new MultiResult<T>(true, SUCCESS.name(), SUCCESS.name(), pageResponse.getDatas(), pageResponse.getTotal(), pageResponse.getCurrentPage(), pageResponse.getPageSize());
        return multiResult;
    }
}
