package pl.devthoughts.spring.web;

import lombok.Getter;

class CreateBookRequest {

    @Getter
    private String title;

    private CreateBookRequest() {}

    CreateBookRequest(String title) {
        this();
        this.title = title;
    }

}
