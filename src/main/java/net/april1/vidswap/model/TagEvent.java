package net.april1.vidswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class TagEvent {

    @Field("id")
    private Integer id;
    private String startOffset;
    private String endOffset;
    private Map<String, Object> tagResource;
    //private List<Map<String, Object>> tagAttributes;
}
