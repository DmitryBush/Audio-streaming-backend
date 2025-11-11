package ohio.rizz.streamingservice.service.artist.mapper;

import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.dto.ArtistDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistCreateMapper {
    Artist mapToArtist(ArtistDto artistDto);
}
