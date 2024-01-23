package project2.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project2.models.Book;
import project2.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if((booksService.findByTitle(book.getTitle())).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием уже существует");
        }
    }
}
