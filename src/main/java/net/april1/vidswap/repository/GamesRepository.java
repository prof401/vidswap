package net.april1.vidswap.repository;

import net.april1.vidswap.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GamesRepository
        extends ReactiveMongoRepository<Game, String> {

    Mono<Game> findByPlaylistId(Integer playlistId);
}

