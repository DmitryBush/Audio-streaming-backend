package ohio.rizz.streamingservice.service.metadata.mapper;

import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.dto.AlbumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumCreateMapper {
    @Mapping(target = "coverArtUrl", source = "artworkDto.objectReference")
    Album mapToAlbum(AlbumDto albumDto);
}
