package ohio.rizz.streamingservice.service.metadata.mapper;

import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.GenreReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreReadMapper {
    GenreReadDto mapToGenreReadDto(Genre genre);
}
