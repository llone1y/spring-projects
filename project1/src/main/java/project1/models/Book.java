package project1.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Book {
    private int id;
    @NotEmpty(message = "Название не должно быть пустым")
    private String title;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+\\s[А-ЯЁA-Z][а-яёa-z]", message = "Автор указывается в формате: Имя Фамилия")
    private String author;
    @Min(value = 0, message = "Год издания не может быть отрицательным")
    private int year;
    private Integer person_id;

    public Book() {
    }

    public Book(int id, String title, String author, int year, int personId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.person_id = personId;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
