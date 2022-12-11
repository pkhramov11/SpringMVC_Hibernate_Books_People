package ru.khramov.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khramov.springcourse.models.Person;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 24.11.2022
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String fullName);
}
