package net.april1.vidswap.converter;

import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.AbstractEvent;
import net.april1.vidswap.model.GenericEvent;
import net.april1.vidswap.model.ShotEvent;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;


@ReadingConverter
@Slf4j
public class EventReadConverter implements Converter<Document, AbstractEvent> {

    public EventReadConverter() {
    }

    @Override
    public AbstractEvent convert(Document source) {
        String eventName = ((Document) source.get("tagResource")).getString("name");
        log.info("Converting {} event", eventName);
        switch (eventName) {
            case "Shot":
                return new ShotEvent(source);
            default:
                return new GenericEvent(source);
        }
    }
}
