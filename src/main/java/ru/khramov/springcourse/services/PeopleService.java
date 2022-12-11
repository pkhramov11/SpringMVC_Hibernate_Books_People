package ru.khramov.springcourse.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khramov.springcourse.models.Book;
import ru.khramov.springcourse.models.Person;
import ru.khramov.springcourse.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundedPerson = peopleRepository.findById(id);
        return foundedPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByFullName (String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book -> {
                long diffInMillis = Math.abs(book.getDateOfTaken().getTime() - new Date().getTime());
                if (diffInMillis > 864000000)
                    book.setExpired(true);
            }
            );
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
