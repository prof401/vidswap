package net.april1.vidswap.converter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.AbstractEvent;
import net.april1.vidswap.model.GenericEvent;
import net.april1.vidswap.model.ShotEvent;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;


@ReadingConverter
@NoArgsConstructor
@Slf4j
public class EventReadConverter implements Converter<Document, AbstractEvent> {

    @Override
    public AbstractEvent convert(Document source) {
        String eventName = ((Document) source.get("tagResource")).getString("name");
        switch (eventName) {
            case "Shot":
                return new ShotEvent(source);
            default:
                log.info("Converting {} event", eventName);
                return new GenericEvent(source);
        }
    }
}
