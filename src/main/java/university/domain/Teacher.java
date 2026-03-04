package university.domain;

import java.time.LocalDate;
import static university.service.Utils.*;

public class Teacher extends Person {
    private String position;
    private String degree;
    private String title;
    private LocalDate hireDate;
    private double rate;

    public Teacher(String id, String lastName, String firstName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   String position, String degree, String title,
                   LocalDate hireDate, double rate) {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        this.position = position;
        this.degree = degree;
        this.title = title;
        this.hireDate = hireDate;
        this.rate = rate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) throws InvalidValue {
        if (containsNonLetter(position)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.position = position;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) throws InvalidValue {
        if (containsNonLetter(degree)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.degree = degree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws InvalidValue {
        if (containsNonLetter(title)) {
            throw new InvalidValue("Назва може містити лише літери");
        }
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) throws InvalidValue {
        if (containsNonDigit(String.valueOf(rate))) {
            throw new InvalidValue("Сума може містити лише числа");
        }
        this.rate = rate;
    }
}
