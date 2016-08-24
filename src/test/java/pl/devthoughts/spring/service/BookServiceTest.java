package pl.devthoughts.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.devthoughts.spring.domain.Book;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.devthoughts.assertj.BookAssert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    private static final String TITLE = "The Shining";
    private static final String TITLE_2 = "The Goldfinch";

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookService bookService;

    @Test
    public void should_store_a_book() {
        String bookUuid = bookService.addBook(TITLE);

        Book foundBook = bookService.getBookByUuid(bookUuid);
        assertThat(foundBook).hasTitle(TITLE);
    }

    @Test
    public void should_delete_book_by_uuid() {
        Book book = new Book(TITLE);
        entityManager.persist(book);

        bookService.deleteBook(book.getUuid());

        assertThat(bookService.getBooks()).isEmpty();
    }

    @Test
    public void should_fail_when_removing_non_existing_book() {
        String randomUuid = UUID.randomUUID().toString();

        assertThatThrownBy(
            () -> bookService.deleteBook(randomUuid))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage("Unable to find a book with UUID " + randomUuid);
    }

    @Test
    public void should_find_book_by_uuid() {
        Book book = new Book(TITLE);
        entityManager.persist(book);

        Book foundBook = bookService.getBookByUuid(book.getUuid());

        assertThat(foundBook).isEqualTo(book);
    }

    @Test
    public void should_fail_when_looking_for_non_existing_book() {
        String randomUuid = UUID.randomUUID().toString();

        assertThatThrownBy(
            () -> bookService.getBookByUuid(randomUuid))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage("Unable to find a book with UUID " + randomUuid);
    }

    @Test
    public void should_update_title_of_book() {
        Book book = new Book(TITLE);
        entityManager.persist(book);

        bookService.updateBook(book.getUuid(), TITLE_2);

        Book foundBook = bookService.getBookByUuid(book.getUuid());
        assertThat(foundBook.getTitle()).isEqualTo(TITLE_2);
    }

    @Test
    public void should_fail_when_updating_non_existing_book() {
        String randomUuid = UUID.randomUUID().toString();

        assertThatThrownBy(
            () -> bookService.updateBook(randomUuid, TITLE_2))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage("Unable to find a book with UUID " + randomUuid);
    }

    @Test
    public void should_find_all_stored_books() {
        Book book = new Book(TITLE);
        entityManager.persist(book);
        Book book2 = new Book(TITLE_2);
        entityManager.persist(book2);

        List<Book> books = bookService.getBooks();

        assertThat(books)
            .hasSize(2)
            .contains(book, book2);
    }

    @Test
    public void should_find_no_books_if_repository_is_empty() {
        List<Book> books = bookService.getBooks();

        assertThat(books).isEmpty();
    }

}
