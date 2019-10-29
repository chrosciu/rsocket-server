package pl.com.sages.rsocketserver;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookRepository {

    private static final Book ONLY_BOOK = Book.builder().author("Mickiewicz").title("Dziady").build();

    public Mono<Book> getBookByTitle(String title) {
        return ONLY_BOOK.getTitle().equals(title) ? Mono.just(ONLY_BOOK) : Mono.empty();
    }
}
