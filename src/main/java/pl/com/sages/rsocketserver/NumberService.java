package pl.com.sages.rsocketserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class NumberService {
    public Mono<Integer> doubleNumber(Integer number) {
        return Mono.just(number).delayElement(Duration.ofSeconds(1L))
                .doOnNext(i -> log.info("Doubled: " +i)).map(i -> i * 2);
    }

    public Mono<Void> acceptNumber(Integer number) {
        return Mono.just(number).delayElement(Duration.ofSeconds(1L))
                .doOnNext(i -> log.info("Accepted: " + i)).then();
    }
}
