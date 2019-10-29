package pl.com.sages.rsocketserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookRepository bookRepository;

    @MessageMapping("find.book")
    public Mono<Book> findBook(Mono<BookQueryDto> dto) {
        return dto.flatMap(d -> bookRepository.getBookByTitle(d.getTitle()));
    }

    @MessageMapping("accept.book")
    public Mono<Void> acceptBook(Mono<Book> book) {
        return book.publishOn(Schedulers.elastic()).doOnNext(b -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}", b);
        }).then();
    }

    @MessageMapping("reverse.books")
    public Flux<Book> reverseBooks(Flux<Book> books, RSocketRequester rSocketRequester) {
        //return books.map(book -> Book.builder().author(book.getTitle()).title(book.getAuthor()).build());
        return books.flatMap(book -> clientReverseBook(book, rSocketRequester)).map(book -> Book.builder().author(book.getTitle()).title(book.getAuthor()).build());
    }

    private Mono<Book> clientReverseBook(Book book, RSocketRequester rSocketRequester) {
        return rSocketRequester
                .route("client.reverse.book")
                .data(book)
                .retrieveMono(Book.class);
    }


}
