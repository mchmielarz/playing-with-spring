package pl.devthoughts.spring.repository;

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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUuid() {
        return uuid;
    }
}
