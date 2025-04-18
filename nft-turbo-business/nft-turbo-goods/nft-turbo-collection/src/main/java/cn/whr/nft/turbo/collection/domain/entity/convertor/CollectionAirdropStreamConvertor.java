package cn.whr.nft.turbo.collection.domain.entity.convertor;

import cn.whr.nft.turbo.api.collection.model.AirDropStreamVO;
import cn.whr.nft.turbo.collection.domain.entity.CollectionAirdropStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author whr
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CollectionAirdropStreamConvertor {

    CollectionAirdropStreamConvertor INSTANCE = Mappers.getMapper(CollectionAirdropStreamConvertor.class);

    /**
     * 转换为vo
     *
     * @param request
     * @return
     */
    @Mapping(target = "airDropTime", source = "request.gmtCreate")
    public AirDropStreamVO mapToVo(CollectionAirdropStream request);

    /**
     * 转换为vo
     *
     * @param request
     * @return
     */
    public List<AirDropStreamVO> mapToVo(List<CollectionAirdropStream> request);
}
