package pl.devthoughts.spring.web;

import lombok.Getter;

import java.util.List;

public class BooksDTO {

    @Getter
    private List<BookDTO> books;

    public BooksDTO(List<BookDTO> books) {
        this.books = books;
    }

}
