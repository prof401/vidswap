package net.april1.vidswap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vidswap")
@Data
@NoArgsConstructor
abstract public class AbstractEvent {
    String name;
    private Integer eventId;
    private String startOffset;
    private String endOffset;
    public AbstractEvent(org.bson.Document source) {
        eventId = source.getInteger("id");
        name = ((org.bson.Document) source.get("tagResource")).getString("name");
        startOffset = source.getString("startOffset");
        endOffset = source.getString("endOffset");
    }

    protected void setAbstractEventFields(AbstractEvent event, org.bson.Document source) {

    }
}
