package net.april1.vidswap.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
import net.april1.vidswap.model.Game;
import net.april1.vidswap.model.GameEvents;
import net.april1.vidswap.repository.GameEventsRepository;
import net.april1.vidswap.repository.GamesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/games")
public class GamesController {
    private GamesRepository gamesRepository;
    private GameEventsRepository eventRepository;

    @GetMapping
    public Flux<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    @GetMapping("/{playlistId}")
    public Mono<Game> getGame(@PathVariable Integer playlistId) {
        return gamesRepository.findByPlaylistId(playlistId);
    }

    @GetMapping("{playlistId}/events")
    public Flux<Event> getAllGameEvents(@PathVariable Integer playlistId) {
        return eventRepository.findAllByPlaylistId(playlistId).flatMapIterable(GameEvents::getTagEvents);
    }

    @GetMapping("{playlistId}/events/shots")
    public Flux<Event> getAllGameShots(@PathVariable Integer playlistId) {
        return this.getAllGameEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("shot"));
    }
}
