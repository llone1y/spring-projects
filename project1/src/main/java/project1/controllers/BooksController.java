package project1.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.dao.BookDAO;
import project1.dao.PersonDAO;
import project1.models.Book;
import project1.models.Person;
import project1.util.BookValidator;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        if(book.getPerson_id() != null) {
            model.addAttribute("reader", personDAO.show(book.getPerson_id()));
        } else {
            model.addAttribute("people", personDAO.index());
        }
        return "books/show";
    }

    @PatchMapping("/{id}/person")
    public String addBookToPerson(@ModelAttribute("person") Person person,
                                  @PathVariable("id") int id) {
        Book book = bookDAO.show(id);
        book.setPerson_id(person.getId());
        bookDAO.update(id, book);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.free(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,@PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }
}
