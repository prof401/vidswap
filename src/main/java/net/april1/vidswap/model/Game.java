package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "vidswap")
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Indexed
    @Field("playlist.id")
    Integer playlistId;
    @Field("playlist.name")
    String name;
    @Field("playlist.date")
    String date;
    @Field("playlist.homeTeam")
    String homeTeam;
    @Field("playlist.awayTeam")
    String awayTeam;
    @Id
    private ObjectId id;
}
