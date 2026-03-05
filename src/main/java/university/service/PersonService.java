package university.service;

import university.domain.Person;
import university.exceptions.PersonAlreadyExistsException;
import university.exceptions.PersonNotFoundException;
import university.repository.Repository;

import java.util.Optional;

public class PersonService {
    private final Repository<Person, String> personRepository;

    public PersonService(Repository<Person, String> repository) {
        this.personRepository = repository;
    }

    public void createPerson(Person person){
        Optional<Person> testCopy = personRepository.findById(person.getID());
        testCopy.ifPresent(
                exists -> {throw new PersonAlreadyExistsException("Не вдалось додати персону з id " + person.getID() + " причина: персона вже існує");}
        );
        personRepository.add(person);
    }
    public void deletePerson(Person person){
        Optional<Person> testCopy = personRepository.findById(person.getID());
        testCopy.orElseThrow(
                () -> new PersonNotFoundException("Не вдалось видалити персону з id " + person.getID() + " причина: не знайдено в репозиторії")
        );
        personRepository.deleteByID(person.getID());
    }
    public void updatePerson(String id, Person person){
        Optional<Person> testCopy = personRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {
                    personRepository.deleteByID(id); personRepository.add(person);},
                () -> { throw new PersonNotFoundException("Не вдалось оновити персону з id " + id + " причина: не знайдено в репозиторії"); }
        );
    }
}
