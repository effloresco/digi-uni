package university.domain;

import university.exceptions.InvalidValue;
import java.time.LocalDate;
import static university.service.Utils.*;
import university.exceptions.*;

public sealed class Person implements Entity<String> permits Student, Teacher {
    private static int counter = 0;

    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public Person() {
        id = String.valueOf(++counter);
    }

    public Person(String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone) throws InvalidValue {
        id = String.valueOf(++counter);
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhone(phone);
    }


    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidValue {
        if (containsNonLetter(lastName)) {
            throw new InvalidValue("Прізвище може містити лише літери");
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidValue {
        if (containsNonLetter(firstName)) {
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) throws InvalidValue {
        if (containsNonLetter(middleName)) {
            throw new InvalidValue("По батькові може містити лише літери");
        }
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidValue {
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidValue("Електронна пошта має містити '@' та '.'");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
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


