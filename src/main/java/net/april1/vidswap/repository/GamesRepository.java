package net.april1.vidswap.repository;

import net.april1.vidswap.model.VidswapGame;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GamesRepository
        extends ReactiveMongoRepository<VidswapGame, String> {

    Mono<VidswapGame> findByPlaylistId(Integer playlistId);

}

