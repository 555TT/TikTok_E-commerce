package cn.whr.nft.turbo.collection.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whr
 */
@Getter
@Setter
public class DestroyParam {

    @NotNull(message = "heldCollectionId is null")
    private String heldCollectionId;

}
