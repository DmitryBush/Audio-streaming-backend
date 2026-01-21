package com.bush.search.service.metadata.genre.mapper;

import com.bush.search.domain.document.Genre;
import com.bush.search.domain.dto.metadata.GenreSearchResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreReadMapper {
    GenreSearchResultDto mapToGenreSearchResultDto(Genre genre);
}
