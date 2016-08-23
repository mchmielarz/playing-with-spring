package pl.devthoughts.spring.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.devthoughts.spring.domain.Book;
import pl.devthoughts.spring.service.BookService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.devthoughts.spring.web.BookDTO.asDto;

@Slf4j
@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/books", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    BookCreatedResponse addBook(@RequestBody CreateBookRequest request) {
        String bookUuid = bookService.addBook(request.getTitle());
        return new BookCreatedResponse(bookUuid);
    }

    @DeleteMapping(path = "/books/{bookUuid}")
    void deleteBook(@PathVariable("bookUuid") String bookUuid) {
        bookService.deleteBook(bookUuid);
    }

    @PutMapping(path = "/books/{bookUuid}", consumes = APPLICATION_JSON_VALUE)
    void updateBook(@PathVariable("bookUuid") String bookUuid, @RequestBody BookDTO book) {
        bookService.updateBook(bookUuid, book.getTitle());
    }

    @GetMapping(path = "/books/{bookUuid}", produces = APPLICATION_JSON_VALUE)
    BookDTO getBook(@PathVariable("bookUuid") String bookUuid) {
        Book book = bookService.getBookByUuid(bookUuid);
        return asDto(book);
    }

    @GetMapping(path = "/books", produces = APPLICATION_JSON_VALUE)
    BooksDTO getAllBooks() {
        List<BookDTO> books = bookService
            .getBooks()
            .stream()
            .map(book -> asDto(book))
            .collect(toList());
        return new BooksDTO(books);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleBookNotFound(IllegalArgumentException ex) {
        log.error("Book not found!", ex);
    }
}
