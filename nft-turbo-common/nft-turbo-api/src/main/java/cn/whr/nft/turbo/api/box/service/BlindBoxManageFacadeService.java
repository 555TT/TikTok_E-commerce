package cn.whr.nft.turbo.api.box.service;

import cn.whr.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.whr.nft.turbo.api.box.response.BlindBoxCreateResponse;

/**
 * 盲盒管理门面服务
 *
 * @author whr
 */
public interface BlindBoxManageFacadeService {

    /**
     * 创建盲盒
     *
     * @param request
     * @return
     */
    BlindBoxCreateResponse create(BlindBoxCreateRequest request);
}
