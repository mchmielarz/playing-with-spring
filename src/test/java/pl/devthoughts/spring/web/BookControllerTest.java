package pl.devthoughts.spring.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.devthoughts.spring.domain.Book;
import pl.devthoughts.spring.service.BookService;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final String BOOK_TITLE = "Soft Skills";
    private static final String BOOK_TITLE_2 = "Good Math";

    private static final String BOOK_UUID = UUID.randomUUID().toString();
    private static final String BOOK_UUID_2 = UUID.randomUUID().toString();

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @Test
    public void should_add_book() throws Exception {
        given(bookService.addBook(BOOK_TITLE)).willReturn(BOOK_UUID);

        mvc.perform(post("/books")
            .content(bookTitle())
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.bookUuid").value(BOOK_UUID));
    }

    @Test
    public void should_find_stored_book() throws Exception {
        given(bookService.getBookByUuid(BOOK_UUID)).willReturn(new Book(BOOK_UUID, BOOK_TITLE));

        mvc.perform(get("/books/" + BOOK_UUID)
            .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid").value(BOOK_UUID))
            .andExpect(jsonPath("$.title").value(BOOK_TITLE));
    }

    @Test
    public void should_fail_when_looking_for_non_exisisting_book() throws Exception {
        given(bookService.getBookByUuid(BOOK_UUID)).willThrow(new IllegalArgumentException("Unable to find a book!"));

        mvc.perform(get("/books/" + BOOK_UUID)
            .accept(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }

    @Test
    public void should_find_all_stored_books() throws Exception {
        given(bookService.getBooks())
            .willReturn(
                Lists.newArrayList(
                    new Book(BOOK_UUID, BOOK_TITLE),
                    new Book(BOOK_UUID_2, BOOK_TITLE_2)
                )
            );

        mvc.perform(get("/books")
            .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.books", hasSize(2)))
            .andExpect(jsonPath("$.books[0].uuid").value(BOOK_UUID))
            .andExpect(jsonPath("$.books[0].title").value(BOOK_TITLE))
            .andExpect(jsonPath("$.books[1].uuid").value(BOOK_UUID_2))
            .andExpect(jsonPath("$.books[1].title").value(BOOK_TITLE_2));
    }

    @Test
    public void should_find_no_books_for_empty_repository() throws Exception {
        given(bookService.getBooks())
            .willReturn(
                Lists.emptyList()
            );

        mvc.perform(get("/books")
            .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.books", hasSize(0)));
    }

    @Test
    public void should_update_existing_book_title() throws Exception {
        mvc.perform(put("/books/" + BOOK_UUID)
            .content(bookDataToUpdate())
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk());
    }

    @Test
    public void should_update_fail_for_non_exisisting_book() throws Exception {
        doThrow(new IllegalArgumentException("Unable to find a book!")).when(bookService).updateBook(eq(BOOK_UUID), eq(BOOK_TITLE_2));

        mvc.perform(put("/books/" + BOOK_UUID)
            .content(bookDataToUpdate())
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }

    @Test
    public void should_remove_existing_book() throws Exception {
        mvc.perform(delete("/books/" + BOOK_UUID))
            .andExpect(status().isOk());
    }

    @Test
    public void should_removal_non_exisisting_book_fail() throws Exception {
        doThrow(new IllegalArgumentException("Unable to find a book!")).when(bookService).deleteBook(eq(BOOK_UUID));

        mvc.perform(delete("/books/" + BOOK_UUID))
            .andExpect(status().isNotFound());
    }

    private String bookTitle() throws JsonProcessingException {
        CreateBookRequest request = new CreateBookRequest(BOOK_TITLE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

    private String bookDataToUpdate() throws JsonProcessingException {
        BookDTO dto = new BookDTO(BOOK_UUID, BOOK_TITLE_2);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

}
