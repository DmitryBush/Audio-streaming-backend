package ohio.rizz.streamingservice.service.genre.mapper;

import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreCreateMapper {
    Genre mapToGenre(GenreDto genreDto);
}
