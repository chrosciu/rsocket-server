package pl.com.sages.rsocketserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

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
}
