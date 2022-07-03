package net.april1.vidswap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
import net.april1.vidswap.model.VidswapGame;
import net.april1.vidswap.model.XGData;
import net.april1.vidswap.service.DataService;
import net.april1.vidswap.service.EventService;
import net.april1.vidswap.service.GameService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

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

    @GetMapping("/events")
    public Flux<Event> getALlEvents() {
        return eventService.getALlEvents().sort(Comparator.comparing(Event::getName));
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
    public Flux<Event> getGameShots(@PathVariable Integer playlistId) {
        return eventService.getShots(playlistId);
    }

    @GetMapping("/{playlistId}/events/periods")
    public Flux<Event> getGamePeriods(@PathVariable Integer playlistId) { return eventService.getPeriods(playlistId); }

    @GetMapping("/{playlistId}/events/setpieces")
    public Flux<Event> getGameSetPieces(@PathVariable Integer playlistId) { return eventService.getSetPieces(playlistId); }

    @GetMapping("/xgdata")
    public Flux<XGData> getXGData() {
        return dataService.collectXGData();
    }

    @GetMapping("/xgdata/csv")
    public Mono<ResponseEntity<String>> getXGDataCSV() {
        return dataService.collectXGData()
                .collectList().map(dataPoint ->
                        ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.csv")
                                .body("ffd;fdfddf;ddddd;")
                );
    }

    @GetMapping("/xgdata/ccc")
    public Flux<XGData> getXGDataCcc() {
        return getXGData()
                .filter(XGData::isConference);
    }

    @GetMapping("/xgdata/csv2")
    public ResponseEntity<Mono<String>> getXGDataCSV2() {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(XGData.class);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.csv")
                .body(dataService.collectXGData().map(data ->
                                        {
                                            try {
                                                return mapper.writerFor(XGData.class)
                                                        .with(schema)
                                                        .writeValueAsString(data);
                                            } catch (JsonProcessingException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                )
                                .reduce("", String::concat)
                );
    }
}
