package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "vidswap")
@AllArgsConstructor
public class Event {

    @Field("tagResource.name")
    String name;
    @Field("id")
    private Integer eventId;
    @Field("startOffset")
    private String startOffset;
    @Field("endOffset")
    private String endOffset;
    @Field("tagAttributes")
    private List<Map<String, Object>> tagAttributes;
}
