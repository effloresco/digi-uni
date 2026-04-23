package university.domain;

import lombok.Getter;
import lombok.Setter;
import university.exceptions.InvalidValue;
import university.repository.DepartmentRepository;
import university.repository.FacultyRepository;

import java.time.LocalDate;
import java.util.Optional;

import static university.service.Utils.*;

@Getter
public non-sealed class Teacher extends Person {
    protected final transient FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final transient DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);
    private String position;
    private String degree;
    private String title;
    private LocalDate hireDate;
    private double rate;
    private String facultyId;
    @Setter
    private String departmentId;

    public Teacher() {
    }

    public Teacher(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String position, String degree, String title, LocalDate hireDate, double rate, String facultyId, String departmentId) throws InvalidValue {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        setPosition(position);
        setDegree(degree);
        setTitle(title);
        setHireDate(hireDate);
        setRate(rate);
        this.facultyId = facultyId;
        this.departmentId = departmentId;
    }

    public void setFacultyId(String facultyId) throws InvalidValue{
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

        if (optionalFaculty.isEmpty()) {
            throw new InvalidValue("Факультет з таким ID не знайдено.");
        }
        this.facultyId = facultyId;
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
        if (rate < 0) {
            throw new InvalidValue("Ставка не може бути від’ємною");
        }
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Teacher{" + "details=" + super.toString() + ", position='" + position + '\'' + ", degree='" + degree + '\'' + ", title='" + title + '\'' + ", hireDate=" + hireDate + ", rate=" + rate + '}';
    }
}
