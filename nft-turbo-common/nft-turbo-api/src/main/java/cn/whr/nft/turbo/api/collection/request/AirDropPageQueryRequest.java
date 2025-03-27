package cn.whr.nft.turbo.api.collection.request;

import cn.whr.nft.turbo.base.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 空投记录分页查询参数
 *
 * @author whr
 */
@Getter
@Setter
public class AirDropPageQueryRequest extends PageRequest {

    private String collectionId;

    private String userId;
}
