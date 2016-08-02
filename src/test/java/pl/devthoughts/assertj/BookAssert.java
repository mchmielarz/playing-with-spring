package pl.devthoughts.assertj;

import org.assertj.core.api.AbstractAssert;
import pl.devthoughts.spring.repository.Book;

import java.util.Objects;

public class BookAssert extends AbstractAssert<BookAssert, Book> {

    public BookAssert(Book actual) {
        super(actual, BookAssert.class);
    }

    public static BookAssert assertThat(Book actual) {
        return new BookAssert(actual);
    }

    public BookAssert hasTitle(String title) {
        isNotNull();
        if (!Objects.equals(actual.getTitle(), title)) {
            failWithMessage("Expected book's title to be <%s> but was <%s>", title, actual.getTitle());
        }
        return this;
    }
}
