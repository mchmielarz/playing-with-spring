package pl.devthoughts.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.devthoughts.spring.domain.Book;
import pl.devthoughts.spring.domain.BookRepository;

import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public String addBook(String bookTitle) {
        Book newBook = new Book(bookTitle);
        Book savedBook = repository.save(newBook);
        return savedBook.getUuid();
    }

    public void deleteBook(String bookUuid) {
        Book foundBook = findBookByUuid(bookUuid);
        deleteFoundBook(foundBook);
    }

    public Book getBookByUuid(String bookUuid) {
        return findBookByUuid(bookUuid);
    }

    public void updateBook(String bookUuid, String bookTitle) {
        Book book = findBookByUuid(bookUuid);
        book.setTitle(bookTitle);
        repository.save(book);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    private Book findBookByUuid(String bookUuid) {
        log.info("Looking for a book by UUID {}", bookUuid);
        return repository
            .findByUuid(bookUuid)
            .orElseThrow(() -> new BookNotFoundException("Unable to find a book with UUID " + bookUuid));
    }

    private void deleteFoundBook(Book foundBook) {
        log.info("Found book {}. Removing it from repository", foundBook.getTitle());
        repository.delete(foundBook);
    }
}
