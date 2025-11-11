package ohio.rizz.streamingservice.service.metadata.mapper;

import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumReadMapper {
    AlbumReadDto mapToAlbumReadDto(Album album);
}
