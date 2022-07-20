package net.april1.vidswap.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Event {
    String name;
    private Integer eventId;
    private String startOffset;
    private String endOffset;
    private List<Map<String, Object>> tagAttributes;

    public Event(VidswapTag vidswapTag) {
        this.name = vidswapTag.getName();
        this.eventId = vidswapTag.getEventId();
        this.startOffset = vidswapTag.getStartOffset();
        this.endOffset = vidswapTag.getEndOffset();
        this.tagAttributes = vidswapTag.getTagAttributes();
    }
}
