package net.april1.vidswap.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.AbstractEvent;
import net.april1.vidswap.model.GameEvents;
import net.april1.vidswap.repository.GameEventsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private GameEventsRepository eventRepository;

    public Flux<AbstractEvent> getEvents(Integer playlistId) {
        return eventRepository.findAllByPlaylistId(playlistId).flatMapIterable(GameEvents::getTagEvents);
    }

    public Flux<AbstractEvent> getShots(Integer playlistId) {
        return getEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("shot"));
    }

    public Flux<AbstractEvent> getTest(Integer playlistId) {
        return getEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("test"));
    }
}
