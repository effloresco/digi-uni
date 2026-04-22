package university.domain;

import lombok.Getter;
import university.exceptions.InvalidValue;
import java.util.UUID;
import static university.service.Utils.containsNonLetter;

@Getter
public class Faculty implements Entity<String> {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty() {
        code = UUID.randomUUID().toString();
    }

    public Faculty(String code, String name, String shortName, Teacher dean, String contacts) throws InvalidValue {
        this.code = code;
        setName(name);
        setShortName(shortName);
        setDean(dean);
        setContacts(contacts);
    }

    public void setName(String name) throws InvalidValue {
        if (containsNonLetter(name)) {
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.name = name;
    }

    public void setShortName(String shortName) throws InvalidValue {
        if (containsNonLetter(shortName)) {
            throw new InvalidValue("Коротке ім'я може містити лише літери");
        }
        this.shortName = shortName;
    }

    public void setDean(Teacher dean) {
        this.dean = dean;
    }

    public void setContacts(String contacts) {
        if (contacts.isEmpty()) throw new InvalidValue("Поле не може бути порожнім");
        this.contacts = contacts;
    }

    @Override
    public String getID() {
        return code;
    }
}

