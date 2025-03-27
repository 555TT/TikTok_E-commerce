package cn.whr.nft.turbo.api.collection.request;

import cn.whr.nft.turbo.base.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Getter
@Setter
public class CollectionPageQueryRequest extends PageRequest {

    private String state;

    private String keyword;

}
