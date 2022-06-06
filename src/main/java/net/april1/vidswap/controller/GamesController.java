package net.april1.vidswap.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
import net.april1.vidswap.model.VidswapTag;
import net.april1.vidswap.model.VidswapGame;
import net.april1.vidswap.service.EventService;
import net.april1.vidswap.service.GameService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/games")
public class GamesController {

    private GameService gameService;
    private EventService eventService;

    @GetMapping
    public Flux<VidswapGame> getAllGames() {
        return gameService.getAll();
    }

    @GetMapping("/{playlistId}")
    public Mono<VidswapGame> getGame(@PathVariable Integer playlistId) {
        return gameService.getGame(playlistId);
    }

    @GetMapping("{playlistId}/events")
    public Flux<Event> getAllGameEvents(@PathVariable Integer playlistId) {
        return eventService.getEvents(playlistId);
    }

    @GetMapping("{playlistId}/events/shots")
    public Flux<Event> getAllGameShots(@PathVariable Integer playlistId) {
        return eventService.getShots(playlistId);
    }
}
