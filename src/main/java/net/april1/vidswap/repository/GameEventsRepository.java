package net.april1.vidswap.repository;

import net.april1.vidswap.model.GameEvents;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameEventsRepository
        extends ReactiveMongoRepository<GameEvents, String> {

    Mono<GameEvents> findAllByPlaylistId(Integer playlistId);

}

