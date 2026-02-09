package university.domain;

import java.time.LocalDate;

class Teacher extends Person {
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

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public void setRate(double rate) {
        this.rate = rate;
    }
}
