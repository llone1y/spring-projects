package project2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project2.models.Book;
import project2.models.Person;


import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>{
    Optional<Book> findByTitle(String title);
    List<Book> findByOwner(Person owner);
    List<Book> findByTitleStartingWithIgnoreCase (String startString);
}
