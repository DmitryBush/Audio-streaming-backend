package com.bush.search.service.metadata.song.mapper;

import com.bush.search.domain.document.Song;
import com.bush.search.domain.index.SongPayload;
import com.bush.search.service.metadata.genre.mapper.GenreCreateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongCreateMapper {
    @Mapping(target = "id", source = "songId")
    @Mapping(target = "artist", source = "artistPayload")
    @Mapping(target = "artist.artistId", source = "artistPayload.id")
    @Mapping(target = "album", source = "albumPayload")
    @Mapping(target = "album.albumId", source = "albumPayload.id")
    @Mapping(target = "album.artist", source = "albumPayload.artistPayload")
    @Mapping(target = "album.artist.artistId", source = "albumPayload.artistPayload.id")
    @Mapping(target = "album.genre", source = "albumPayload.genrePayload")
    @Mapping(target = "album.genre.genreId", source = "albumPayload.genrePayload.id")
    Song mapToSong(SongPayload songPayload);
}
