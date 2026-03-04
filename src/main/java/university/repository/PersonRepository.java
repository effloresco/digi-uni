package university.repository;

import university.domain.Person;

public class PersonRepository extends Repository<Person, String>{
    private static PersonRepository personRepository;

    public static PersonRepository get(){
        if (personRepository == null){
            personRepository = new PersonRepository();
        }
        return personRepository;
    }
}
