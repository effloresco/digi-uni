package university.domain;

import university.exceptions.InvalidValue;
import static university.service.Utils.containsNonDigit;
import static university.service.Utils.containsNonLetter;

public class Faculty implements Entity<String> {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty() {
    }

    public void setId(String code) throws InvalidValue {
        if (containsNonDigit(code)) {
            throw new InvalidValue("Code може містити лише літери");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValue {
        if (containsNonLetter(name)) {
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) throws InvalidValue {
        if (containsNonLetter(shortName)) {
            throw new InvalidValue("Коротке ім'я може містити лише літери");
        }
        this.shortName = shortName;
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        this.dean = dean;
    }

    public String getContacts() {
        return contacts;
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

