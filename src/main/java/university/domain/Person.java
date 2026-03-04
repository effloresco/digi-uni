package university.domain;

import university.exceptions.InvalidValue;

import java.time.LocalDate;
import static university.service.Utils.*;

public class Person implements Entity<String>{
    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public Person(String id, String lastName, String firstName, String middleName,
                  LocalDate birthDate, String email, String phone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidValue {
        if(containsNonDigit(lastName)){
            throw new InvalidValue("Прізвище може містити лише літери");
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidValue{
        if(containsNonDigit(firstName)){
            throw new InvalidValue("Ім'я може містити лише літери");
        }
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) throws InvalidValue{
        if(containsNonDigit(middleName)){
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

    public void setEmail(String email) throws InvalidValue{
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidValue("Електронна пошта має містити '@' та '.'");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws InvalidValue{

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
}
