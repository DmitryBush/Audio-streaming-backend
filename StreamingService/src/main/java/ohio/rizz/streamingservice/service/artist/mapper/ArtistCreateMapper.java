package ohio.rizz.streamingservice.service.artist.mapper;

import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.dto.ArtistDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtistCreateMapper {
    Artist mapToArtist(ArtistDto artistDto);
}
