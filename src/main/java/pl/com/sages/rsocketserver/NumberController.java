package pl.com.sages.rsocketserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NumberController {
    private final NumberService numberService;

    @MessageMapping("double.number")
    public Mono<Integer> doubleNumber(Mono<Integer> number) {
        return number.flatMap(numberService::doubleNumber);
    }

    @MessageMapping("accept.number")
    public Mono<Void> acceptNumber(Mono<Integer> number) {
        return number.flatMap(numberService::acceptNumber);
    }

    @MessageMapping("chat.number")
    public Flux<Integer> chatNumber(Flux<Integer> inbound) {
        inbound.subscribe(i -> log.info("inbound: " + i), e -> log.warn("", e));
        Flux<Integer> outbound = Flux.interval(Duration.ofSeconds(5)).map(l -> (int)(long)l);
        return outbound;
    }
}
