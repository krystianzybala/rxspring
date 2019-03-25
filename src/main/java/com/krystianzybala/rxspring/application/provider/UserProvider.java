package com.krystianzybala.rxspring.application.provider;

import com.krystianzybala.rxspring.application.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
public class UserProvider {

    public void getUsers() {

    }


    public Mono<User> getUserByName(final String name) {

        return Mono
                .fromCallable(() -> new User("Joe"))
                .delaySubscription(Duration.ofSeconds(10));
    }
}
