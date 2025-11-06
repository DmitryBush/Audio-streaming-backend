package ohio.rizz.streamingservice.service.metadata.mapper;

import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.dto.AlbumDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumCreateMapper {
    Album mapToAlbum(AlbumDto albumDto);
}
