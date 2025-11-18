package ohio.rizz.streamingservice.service.genre.mapper;

import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreCreateMapper {
    @Mapping(target = "id", ignore = true)
    Genre mapToGenre(GenreDto genreDto);
}
