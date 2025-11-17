package ohio.rizz.streamingservice.service.song.mapper;

import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.dto.song.SongDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongCreateMapper {
    Song mapToSong(SongDto songDto);
}
