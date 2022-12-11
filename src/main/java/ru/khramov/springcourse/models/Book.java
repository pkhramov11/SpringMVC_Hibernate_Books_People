package ru.khramov.springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min=2, max=100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min=2, max=100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    @Column(name = "author")
    private String author;
    @Min(value = 868, message = "Год издания не должен быть меньше 868")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    //name - имя столбца таблице Book, хранящий id читателя, referencedColomnName - имя столбца в таблице Person, хранящий id человека
    private Person reader;

    @Column(name = "date_of_taken")
    private Date dateOfTaken;

    @Transient//Hibernate не обращает внимание на это поле
    private boolean expired;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {
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

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
    }

    public Date getDateOfTaken() {
        return dateOfTaken;
    }

    public void setDateOfTaken(Date dateOfTaken) {
        this.dateOfTaken = dateOfTaken;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
