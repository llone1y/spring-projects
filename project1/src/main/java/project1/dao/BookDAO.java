package project1.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project1.models.Book;
import project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Optional<Book> show(String bookTitle) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title=?", new Object[]{bookTitle}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id = ?, title = ?, author = ?, year = ? WHERE id = ?",
                book.getPerson_id(), book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE Book set person_id = NULL where id = ?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }
    public List<Book> showBooksByPerson(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new BeanPropertyRowMapper<>(Book.class), person_id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }
}
