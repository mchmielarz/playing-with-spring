package pl.devthoughts.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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

}
