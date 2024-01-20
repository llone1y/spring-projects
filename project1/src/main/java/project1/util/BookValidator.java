package project1.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project1.dao.BookDAO;
import project1.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if((bookDAO.show(book.getTitle())).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием уже существует");
        }
    }
}
