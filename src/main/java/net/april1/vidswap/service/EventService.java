package net.april1.vidswap.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
import net.april1.vidswap.model.VidswapTag;
import net.april1.vidswap.model.VidswapGameTags;
import net.april1.vidswap.repository.GameEventsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private GameEventsRepository eventRepository;

    public Flux<Event> getEvents(Integer playlistId) {
        return eventRepository.findAllByPlaylistId(playlistId)
                .flatMapIterable(VidswapGameTags::getTagEvents)
                .map(Event::new);
    }

    public Flux<Event> getShots(Integer playlistId) {
        return getEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("shot"));
    }
}
