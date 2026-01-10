package com.bush.search.service.metadata.song.mapper;

import com.bush.search.domain.document.Song;
import com.bush.search.domain.index.SongPayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongCreateMapper {
    Song mapToSong(SongPayload songPayload);
}
