package pl.devthoughts.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.devthoughts.assertj.BookAssert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

    private static final String TITLE = "Dune";

    @Autowired
    Environment environment;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void should_find_a_book_by_title() {
        entityManager.persist(new Book(TITLE));

        Book book = bookRepository.findByTitle(TITLE);

        assertThat(book).hasTitle(TITLE);
    }

    @Test
    public void should_delete_a_book_by_its_uuid() {
        final Book book = entityManager.persist(new Book(TITLE));

        List<Book> deletedBook = bookRepository.deleteByUuid(book.getUuid());
        assertThat(deletedBook).contains(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).isEmpty();
    }

}
