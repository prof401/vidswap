package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "vidswap")
@AllArgsConstructor
public class VidswapGameTags {

    @Indexed
    @Field("playlist.id")
    Integer playlistId;

    List<VidswapTag> tagEvents;
}
