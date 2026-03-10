package university.domain;

import university.exceptions.InvalidValue;
import university.repository.TeacherRepository;

import java.util.Optional;

import static university.service.Utils.containsNonDigit;
import static university.service.Utils.containsNonLetter;

public class Faculty implements Entity<String> {
    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty(String code, String name, String shortName, Teacher dean, String contacts) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.dean = dean;
        this.contacts = contacts;
    }

    public void setCode(String code) throws InvalidValue {
        if (containsNonDigit(code)) {
            throw new InvalidValue("Code може містити лише літери");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValue{
        if(containsNonLetter(name)){
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) throws InvalidValue{
        if(containsNonLetter(shortName)){
            throw new InvalidValue("Коротке ім'я може містити лише літери");
        }
        this.shortName = shortName;
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(String deanId) throws  InvalidValue{
        Optional<Teacher> optionalPerson = teacherRepository.findById(deanId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (!(person instanceof Teacher)) {
                throw new InvalidValue("Ця особа не є викладачем");
            }
        } else {
            throw new InvalidValue("Особу з таким ID не знайдено.");
        }
        this.dean = dean;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public String getID() {
        return code;
    }
}

