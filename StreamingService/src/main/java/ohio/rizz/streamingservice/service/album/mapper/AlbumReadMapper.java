package ohio.rizz.streamingservice.service.album.mapper;

import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumReadMapper {
    @Mapping(target = "genreDto", source = "genre")
    AlbumReadDto mapToAlbumReadDto(Album album);
}
