package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "vidswap")
@AllArgsConstructor
public class Event {

    @Field("id")
    private Integer eventId;

    @Field("startOffset")
    private String startOffset;

    @Field("endOffset")
    private String endOffset;

    @Field("tagResource.name")
    String name;

    //private Map<String, Object> tagResource;
    //private List<Map<String, Object>> tagAttributes;
}
