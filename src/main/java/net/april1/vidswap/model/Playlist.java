package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Indexed
    @Field("id")
    Integer id;
    String name;
    String date;
    String homeTeam;
    String awayTeam;

}
