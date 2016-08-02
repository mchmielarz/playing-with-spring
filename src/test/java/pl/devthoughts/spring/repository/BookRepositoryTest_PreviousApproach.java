package pl.devthoughts.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.SpringApplicationContextLoader;
import pl.devthoughts.spring.Application;

import static pl.devthoughts.assertj.BookAssert.assertThat;

@ActiveProfiles("test")
@ContextConfiguration(classes = { Application.class }, loader = SpringApplicationContextLoader.class)
@RunWith(SpringRunner.class)
public class BookRepositoryTest_PreviousApproach {

    private static final String TITLE = "Dune";

    @Autowired
    BookRepository bookRepository;

    @Test
    public void should_find_a_book_by_title() {
        bookRepository.save(new Book(TITLE));

        Book book = bookRepository.findByTitle(TITLE);

        assertThat(book).hasTitle(TITLE);
    }

}
