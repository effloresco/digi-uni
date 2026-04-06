package university.domain;

import lombok.Getter;
import university.exceptions.InvalidValue;

import java.time.LocalDate;

import static university.service.Utils.*;

@Getter
public non-sealed class Teacher extends Person {
    private String position;
    private String degree;
    private String title;
    private LocalDate hireDate;
    private double rate;

    public Teacher() {
    }

    public Teacher(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String position, String degree, String title, LocalDate hireDate, double rate) throws InvalidValue {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        setPosition(position);
        setDegree(degree);
        setTitle(title);
        setHireDate(hireDate);
        setRate(rate);
    }

    public void setPosition(String position) throws InvalidValue {
        if (containsNonLetter(position)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.position = position;
    }

    public void setDegree(String degree) throws InvalidValue {
        if (containsNonLetter(degree)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.degree = degree;
    }

    public void setTitle(String title) throws InvalidValue {
        if (containsNonLetter(title)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.title = title;
    }

    public void setHireDate(LocalDate hireDate) throws InvalidValue {
        if (hireDate.toString().isEmpty()) {
            throw new InvalidValue("Поле не може бути порожнім");
        }
        this.hireDate = hireDate;
    }

    public void setRate(double rate) throws InvalidValue {
        if (containsNonDigit(String.valueOf(rate))) {
            throw new InvalidValue("Сума може містити лише числа");
        }
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Teacher{" + "details=" + super.toString() + ", position='" + position + '\'' + ", degree='" + degree + '\'' + ", title='" + title + '\'' + ", hireDate=" + hireDate + ", rate=" + rate + '}';
    }
}
