package com.bush.search.service.metadata.genre.mapper;

import com.bush.search.domain.document.Genre;
import com.bush.search.domain.index.GenrePayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreCreateMapper {
    Genre mapToGenre(GenrePayload genrePayload);
}
