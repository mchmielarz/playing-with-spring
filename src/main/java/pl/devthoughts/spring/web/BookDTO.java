package pl.devthoughts.spring.web;

import lombok.Getter;
import pl.devthoughts.spring.domain.Book;

class BookDTO {

    @Getter
    private String uuid;
    @Getter
    private String title;

    private BookDTO() {}

    public BookDTO(String uuid, String title) {
        this();
        this.uuid = uuid;
        this.title = title;
    }

    static BookDTO asDto(Book book) {
        return new BookDTO(book.getUuid(), book.getTitle());
    }
}
