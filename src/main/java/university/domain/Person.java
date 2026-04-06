package university.domain;

import lombok.Getter;
import university.exceptions.InvalidValue;
import java.time.LocalDate;
import static university.service.Utils.*;

@Getter
public sealed class Person implements Entity<String> permits Student, Teacher {
    @Getter
    private static int idCounter = 0;

    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public Person() {
        id = String.valueOf(++idCounter);
    }

    public Person(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone) throws InvalidValue {
        this.id = id;
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhone(phone);
    }

    public void setLastName(String lastName) throws InvalidValue {
        if (containsNonLetter(lastName)) {
            throw new InvalidValue("Прізвище може містити лише літери");
        }
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) throws InvalidValue {
        if (containsNonLetter(firstName)) {
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) throws InvalidValue {
        if (containsNonLetter(middleName)) {
            throw new InvalidValue("По батькові може містити лише літери");
        }
        this.middleName = middleName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) throws InvalidValue {
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidValue("Електронна пошта має містити '@' та '.'");
        }
        this.email = email;
    }

    public void setPhone(String phone) throws InvalidValue {

        if (containsNonDigit(phone) || phone.length() > 12 || phone.length() < 10) {
            throw new InvalidValue("Потрібно вказати дійсний номер телефону");
        }
        this.phone = phone;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" + "id='" + id + '\'' + ", lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + ", middleName='" + middleName + '\'' + ", birthDate=" + birthDate + ", email='" + email + '\'' + ", phone='" + phone + '\'' + '}';
    }

}


