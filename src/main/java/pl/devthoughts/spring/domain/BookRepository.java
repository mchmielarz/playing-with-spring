package pl.devthoughts.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    Optional<Book> findByUuid(String uuid);

    Long deleteByUuid(String uuid);

}
