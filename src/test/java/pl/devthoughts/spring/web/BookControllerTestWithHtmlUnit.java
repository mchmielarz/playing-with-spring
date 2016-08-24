package pl.devthoughts.spring.web;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.devthoughts.spring.domain.Book;
import pl.devthoughts.spring.service.BookService;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTestWithHtmlUnit {

    private static final String BOOK_TITLE = "Soft Skills";
    private static final String BOOK_TITLE_2 = "Good Math";

    private static final String BOOK_UUID = UUID.randomUUID().toString();
    private static final String BOOK_UUID_2 = UUID.randomUUID().toString();

    @Autowired
    WebClient webClient;

    @MockBean
    BookService bookService;

    @Test
    public void should_provide_books_as_html() throws Exception {
        Book softSkills = new Book(BOOK_UUID, BOOK_TITLE);
        Book goodMath = new Book(BOOK_UUID_2, BOOK_TITLE_2);
        given(bookService.getBooks())
            .willReturn(
                newArrayList(softSkills, goodMath)
            );

        HtmlPage booksPage = webClient.getPage("/books.html");

        assertThat(booksPage.getBody().getTextContent())
            .contains(softSkills.toString())
            .contains(goodMath.toString());
    }
}
