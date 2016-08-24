package pl.devthoughts.spring.domain;

import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "title")
    private String title;

    private Book() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Book(String title) {
        this();
        this.title = title;
    }

    public Book(String uuid, String title) {
        this.uuid = uuid;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append(uuid)
            .append(": ")
            .append(title)
            .toString();
    }
}
