package net.april1.vidswap.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
import net.april1.vidswap.model.VidswapGame;
import net.april1.vidswap.model.XGData;
import net.april1.vidswap.service.DataService;
import net.april1.vidswap.service.EventService;
import net.april1.vidswap.service.GameService;
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

    private GameService gameService;
    private EventService eventService;
    private DataService dataService;

    @GetMapping
    public Flux<VidswapGame> getAllGames() {
        return gameService.getAll();
    }

    @GetMapping("/{playlistId}")
    public Mono<VidswapGame> getGame(@PathVariable Integer playlistId) {
        return gameService.getGame(playlistId);
    }

    @GetMapping("/{playlistId}/events")
    public Flux<Event> getAllGameEvents(@PathVariable Integer playlistId) {
        return eventService.getEvents(playlistId);
    }

    @GetMapping("/{playlistId}/events/shots")
    public Flux<Event> getAllGameShots(@PathVariable Integer playlistId) {
        return eventService.getShots(playlistId);
    }

    @GetMapping("/xgdata")
    public Flux<XGData> getXGData() {
        return dataService.collectXGData();
    }
}
