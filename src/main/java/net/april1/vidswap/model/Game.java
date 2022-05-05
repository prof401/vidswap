package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "vidswap")
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    private ObjectId id;
    private Playlist playlist;
    private List<TagEvent> tagEvents;
}
