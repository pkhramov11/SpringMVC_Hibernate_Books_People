package ru.khramov.springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khramov.springcourse.models.Book;
import ru.khramov.springcourse.models.Person;
import ru.khramov.springcourse.repositories.BooksRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;

    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(int page, int itemsPerPage, boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundedBook = booksRepository.findById(id);
        return foundedBook.orElse(null);
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
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookReader(int id) {
        return booksRepository.findById(id).map(Book::getReader).orElse(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setReader(selectedPerson);
                    book.setDateOfTaken(new Date());
                }
        );
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setReader(null);
                    book.setDateOfTaken(null);
                }
        );
    }
    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleStartingWith(query);
    }
}
