package net.april1.vidswap.repository;

import net.april1.vidswap.model.VidswapGameTags;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameEventsRepository
        extends ReactiveMongoRepository<VidswapGameTags, String> {

    Mono<VidswapGameTags> findAllByPlaylistId(Integer playlistId);

}

