package net.april1.vidswap.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "vidswap")
@Data
@Slf4j
public class ShotEvent {
    String teamName;
    String result;
    Double net_x;
    Double net_y;
    Integer net_sector;
    Double field_x;
    Double field_y;
    Integer field_sector;
    String goalkeeper;
    String athlete;
    private Map<String, String> other = new HashMap<>();

    public ShotEvent(org.bson.Document source) {
     //   super(source);

        List<Object> tagAttributeList = (List) source.get("tagAttributes");
        for (Object o : tagAttributeList) {
            org.bson.Document attributeDocument = (org.bson.Document) o;
            var attributeName = attributeDocument.getString("name");
            var attributeValue = attributeDocument.get("value");
            switch (attributeName) {
                case "Team":
                    setTeamName((String) attributeValue);
                    break;
                case "Result":
                    setResult((String) attributeValue);
                    break;
                case "Goalkeeper":
                    setGoalkeeper((String) attributeValue);
                    break;
                case "Athlete":
                    setAthlete((String) attributeValue);
                    break;
                case "Net Location":
                    org.bson.Document net = (org.bson.Document) attributeValue;
                    setNet_sector(net.getInteger("sector"));
                    setNet_x(Double.parseDouble(net.get("x").toString()));
                    setNet_y(Double.parseDouble(net.get("y").toString()));
                    break;
                case "Field Location":
                    org.bson.Document field = (org.bson.Document) attributeValue;
                    setField_sector(field.getInteger("sector"));
                    setField_x(Double.parseDouble(field.get("x").toString()));
                    setField_y(Double.parseDouble(field.get("y").toString()));
                    break;
                case "Assist":
                case "Own Goal":
                case "Assist Description":
                case "Save Description":
                case "Goal Mouth":
                case "Body Location":
                case "From":
                case "Block Athlete":
                case "Save Body":
                    if (attributeValue instanceof String)
                        other.put(attributeName, (String) attributeValue);
                    else
                        other.put(attributeName, "Document");
                    break;
                default:
                    log.info("Missing {} ", attributeName);
                    if (attributeValue instanceof String)
                        other.put(attributeName, (String) attributeValue);
                    else
                        other.put(attributeName, "Document");
                    break;

            }
        }
    }
}
