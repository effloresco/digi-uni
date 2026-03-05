package university.service;

import university.domain.Faculty;
import university.domain.Person;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
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
    public void updatePerson(String currentId, Person person){
        Optional<Person> testCopy = personRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось оновити персону з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = person.getID();
        if(!currentId.equals(newId)){
            personRepository.findById(newId).ifPresent(
                    exists -> {throw new FacultyAlreadyExistsException("Не вдалось оновити персону з id " + currentId + " причина: персона з id " + newId + " вже існує");}

            );
        }
        personRepository.deleteByID(currentId);
        personRepository.add(person);
    }
}
