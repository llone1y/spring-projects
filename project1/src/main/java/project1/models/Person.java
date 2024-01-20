package project1.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


public class Person {
    private int id;
    @NotEmpty(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+\\s[А-ЯЁA-Z][а-яёa-z]+\\s[А-ЯЁA-Z][а-яёa-z]+$", message = "Должно соответствовать формату: Имя Фамилия Очество")
    private String name;

    @Min(value = 0, message = "Год рождения не может быть отрицательным")
    private int year;

    public Person() {
    }

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
