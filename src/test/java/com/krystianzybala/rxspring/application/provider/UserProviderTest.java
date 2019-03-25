package com.krystianzybala.rxspring.application.provider;

import com.krystianzybala.rxspring.application.dto.User;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

import static org.junit.Assert.*;

public class UserProviderTest {

    private UserProvider userProvider;

    @Before
    public void setUp() throws Exception {
        this.userProvider = new UserProvider();
    }

    @Test
    public void testLoadUserByNameWithDelay() {

        // setup
        final VirtualTimeScheduler virtualTimeScheduler = VirtualTimeScheduler.create();

        final StepVerifier.Step<User> step = StepVerifier
                .withVirtualTime(() -> this.userProvider.loadUserByName("Joe"), () -> virtualTimeScheduler, 1)
                .expectSubscription();


        // when
        virtualTimeScheduler.advanceTimeBy(Duration.ofSeconds(10));


        // then
        step
                .assertNext(user -> assertEquals(new User("Joe"), user))
                .verifyComplete();

    }


    @Test
    public void testWhenSubcriptionTimeNotYetOccured() {

        // setup
        final StepVerifier.Step<User> step = StepVerifier
                .withVirtualTime(() -> this.userProvider.loadUserByName("Joe"))
                .expectSubscription();

        // no events occurs
        step.expectNoEvent(Duration.ofSeconds(9));


        //  shift time to 10 second
        step.thenAwait(Duration.ofSeconds(10));


        //then
        step
                .assertNext(user -> assertEquals(new User("Joe"), user))
                .verifyComplete();
    }


    @Test
    public void testSumOccursNamesWithLengthLessThanFourChars() {

        // setup
        final Flux<User> users = this.userProvider.getUsers();


        // when

        final Mono<Long> count = users
                .filter(user -> user.getName().length() <= 3)
                .count()
                .switchIfEmpty(Mono.just((0L))); // simple fallback - don't do this.


        // then

        StepVerifier
                .create(count)
                .expectSubscription()
                .expectNext(3L)
                .verifyComplete();

    }
}