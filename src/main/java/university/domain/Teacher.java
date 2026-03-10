package university.domain;

import university.exceptions.InvalidValue;
import java.time.LocalDate;
import static university.service.Utils.*;

public class Teacher extends Person {
    private String position;
    private String degree;
    private String title;
    private LocalDate hireDate;
    private double rate;

    public Teacher(){};

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

    @Override
    public String toString() {
        return "Teacher{" +
                "details=" + super.toString() + // Виклик toString() з Person
                ", position='" + position + '\'' +
                ", degree='" + degree + '\'' +
                ", title='" + title + '\'' +
                ", hireDate=" + hireDate +
                ", rate=" + rate +
                '}';
    }
}
