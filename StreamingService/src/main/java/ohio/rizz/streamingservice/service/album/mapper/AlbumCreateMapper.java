package ohio.rizz.streamingservice.service.album.mapper;

import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.dto.AlbumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumCreateMapper {
    @Mapping(target = "coverArtUrl", source = "artworkDto.objectReference")
    Album mapToAlbum(AlbumDto albumDto);
}
