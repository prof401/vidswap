package net.april1.vidswap.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vidswap")
@Data
public class ShotEvent extends AbstractEvent {
    public ShotEvent(org.bson.Document source) {
        super(source);
    }
}
