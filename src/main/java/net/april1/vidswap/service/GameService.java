package net.april1.vidswap.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.VidswapGame;
import net.april1.vidswap.repository.GamesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class GameService {
    private GamesRepository gamesRepository;

    public Flux<VidswapGame> getAll() {
        return gamesRepository.findAll();
    }

    public Mono<VidswapGame> getGame(Integer playlistId) {
        return gamesRepository.findByPlaylistId(playlistId);
    }
}
