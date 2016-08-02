package pl.devthoughts.spring.repository;

import javax.persistence.*;

@Entity
@Table(name = "ENTITIES")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    private Book() {}

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

    public void setTitle(String title) {
        this.title = title;
    }
}
