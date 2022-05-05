package net.april1.vidswap.controller;

import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Game;
import net.april1.vidswap.repository.VidswapGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class GamesController {

    @Autowired
    private VidswapGameRepository vidswapRepository;

    @GetMapping("/games")
    public Flux<Game> getAllGames() {
        return vidswapRepository.findAll();
    }

    @GetMapping("/games/{playlistId}")
    public Mono<Game> getGame(@PathVariable Integer playlistId){
        return vidswapRepository.findByPlaylistId(playlistId).log();
    }
}
