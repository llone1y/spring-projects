package project2.controllers;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project2.models.Book;
import project2.models.Person;
import project2.services.BooksService;
import project2.services.PeopleService;
import project2.util.BookValidator;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "books_per_page", required = false) Integer booksPerPage, @RequestParam(value = "sort_by_year", required = false) boolean sort) {
        switch (page != null ? (sort ? 2 : 1) : (sort ? 3 : 0)) {
            case 0:
                model.addAttribute("books", booksService.findAll());
                break;
            case 1:
                model.addAttribute("books", booksService.findAllWithPagination(page, booksPerPage));
                break;
            case 2:
                model.addAttribute("books", booksService.findAllWithPaginationSorted(page, booksPerPage));
                break;
            case 3:
                model.addAttribute("books", booksService.findAllSortByYear());
                break;
        }
        return "books/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "title", required = false)String title, Model model) {
        if(StringUtils.isNotBlank(title)) {
            model.addAttribute("books", booksService.searchBooks(title));
        }
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        if(book.getOwner() != null) {
            model.addAttribute("reader", book.getOwner());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @PatchMapping("/{id}/person")
    public String addBookToPerson(@ModelAttribute("person") Person person,
                                  @PathVariable("id") int id) {
        booksService.addBookToPerson(person, id);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        booksService.free(id);
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

        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,@PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }
}
