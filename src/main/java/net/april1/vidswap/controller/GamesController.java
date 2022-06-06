package net.april1.vidswap.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.AbstractEvent;
import net.april1.vidswap.model.Game;
import net.april1.vidswap.model.GameEvents;
import net.april1.vidswap.repository.GameEventsRepository;
import net.april1.vidswap.service.GameService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/games")
public class GamesController {
    private GameEventsRepository eventRepository;
    private GameService gameService;

    @GetMapping
    public Flux<Game> getAllGames() {
        return gameService.getAll();
    }

    @GetMapping("/{playlistId}")
    public Mono<Game> getGame(@PathVariable Integer playlistId) {
        return gameService.getGame(playlistId);
    }

    @GetMapping("{playlistId}/events")
    public Flux<AbstractEvent> getAllGameEvents(@PathVariable Integer playlistId) {
        return eventRepository.findAllByPlaylistId(playlistId).flatMapIterable(GameEvents::getTagEvents);
    }

    @GetMapping("{playlistId}/events/shots")
    public Flux<AbstractEvent> getAllGameShots(@PathVariable Integer playlistId) {
        return this.getAllGameEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("shot"));
    }
    @GetMapping("{playlistId}/events/test")
    public Flux<AbstractEvent> getAllGameTest(@PathVariable Integer playlistId) {
        return this.getAllGameEvents(playlistId).filter(e -> e.getName().equalsIgnoreCase("test"));
    }

//    @GetMapping("export")
//    @ResponseBody
//    public ResponseEntity<Mono<Resource>> downloadCsv() {
//        String fileName = String.format("%s.csv", RandomStringUtils.randomAlphabetic(10));
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + fileName)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                .body(csvWriterService.generateCsv()
//                        .flatMap(x -> {
//                            Resource resource = new InputStreamResource(x);
//                            return Mono.just(resource);
//                        }));
//    }
}
