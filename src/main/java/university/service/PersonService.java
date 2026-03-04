package university.service;

import university.domain.Person;
import university.repository.Repository;

import java.util.Optional;

public class PersonService {
    private final Repository<Person, String> personRepository;

    public PersonService(Repository<Person, String> repository) {
        this.personRepository = repository;
    }

    public void createPerson(Person person) {
        Optional<Person> testCopy = personRepository.findById(person.getID());
        if (testCopy.isPresent()) {
            throw new IllegalArgumentException("Не можна створити особу з існуючим id");
        }

        personRepository.add(person);
    }

    public void deletePerson(Person person) {
        Optional<Person> testCopy = personRepository.findById(person.getID());
        testCopy.orElseThrow(
                () -> new IllegalArgumentException("Не існує такої особи")
        );
        personRepository.deleteByID(person.getID());
    }

    public void updatePerson(String id, Person person) {
        Optional<Person> testCopy = personRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {
                    personRepository.deleteByID(id);
                    personRepository.add(person);
                },
                () -> {
                    throw new IllegalArgumentException("Не існує такої особи");
                }
        );
    }
}
