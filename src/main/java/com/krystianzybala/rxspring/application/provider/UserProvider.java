package com.krystianzybala.rxspring.application.provider;

import com.krystianzybala.rxspring.application.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Service
public class UserProvider {

    Flux<User> getUsers() {
        log.info("Get users");
        return Mono.fromCallable(() -> Arrays.asList("Tom", "Joe", "George", "Harry", "Ron"))
                .flux()
                .concatMap(Flux::fromIterable)
                .map(User::new);
    }


    Mono<User> loadUserByName(final String name) {

        log.info("Load user by name: {}", name);
        return Mono
                .fromCallable(() -> new User("Joe"))
                .delaySubscription(Duration.ofSeconds(10));
    }
}
