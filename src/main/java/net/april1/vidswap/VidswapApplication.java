package net.april1.vidswap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class VidswapApplication {

    public static void main(String[] args) {
        SpringApplication.run(VidswapApplication.class, args);
    }
}
