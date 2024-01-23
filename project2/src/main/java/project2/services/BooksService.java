package project2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project2.models.Book;
import project2.models.Person;
import project2.repositories.BooksRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllSortByYear() {
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> findAllWithPaginationSorted(Integer page, Integer booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Optional<Book> findByTitle(String title){
        return booksRepository.findByTitle(title);
    }
    public List<Book> searchBooks(String title) {
        return booksRepository.findByTitleStartingWithIgnoreCase(title);
    }

    @Transactional
    public void addBookToPerson(Person person, int id) {
        Book book = booksRepository.findById(id).orElse(null);
        if(book != null) {
            book.setOwner(person);
            book.setTakenAt(LocalDateTime.now());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void free(int id) {
        Book book = findOne(id);
        if(book != null) {
            book.setOwner(null);
            book.setExpired(false);
            book.setTakenAt(null);
            booksRepository.save(book);
        }
    }
    @Transactional
    public List<Book> showBooksByPerson(Person person) {
        List<Book> books = booksRepository.findByOwner(person);
        books.forEach(book -> book.setExpired(expiredCheck(book)));
        return books;
    }

    public boolean expiredCheck(Book book) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenDaysAgo = now.minusDays(10);
        return book.getTakenAt().isBefore(tenDaysAgo);
    }

}
