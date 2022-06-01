package net.april1.vidswap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "vidswap")
@Data
@NoArgsConstructor
public class GenericEvent extends AbstractEvent {
    private Map<String, String> tagAttributes = new HashMap<>();

    public GenericEvent(org.bson.Document source) {
        super(source);

        List<Object> tagAttributeList = (List) source.get("tagAttributes");
        for (Object o : tagAttributeList) {
            org.bson.Document attributeDocument = (org.bson.Document) o;
            tagAttributes.put(attributeDocument.getString("name"), attributeDocument.getString("value"));
        }
    }
}
