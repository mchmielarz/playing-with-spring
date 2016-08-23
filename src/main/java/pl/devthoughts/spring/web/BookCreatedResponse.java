package pl.devthoughts.spring.web;

import lombok.Getter;

class BookCreatedResponse {

    @Getter
    private String bookUuid;

    BookCreatedResponse(String bookUuid) {
        this.bookUuid = bookUuid;
    }
}
