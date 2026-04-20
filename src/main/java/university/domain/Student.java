package university.domain;

import lombok.Getter;
import university.exceptions.InvalidValue;
import university.repository.DepartmentRepository;
import university.repository.FacultyRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Optional;

import static university.service.Utils.*;

@Getter
public non-sealed class Student extends Person {
    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);

    public enum StudyForm {BUDGET, CONTRACT}

    public enum StudentStatus {STUDYING, ACADEMIC_LEAVE, EXPELLED}

    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private StudyForm form;
    private StudentStatus status;
    private String facultyId;
    private String specialty;
    private String departmentId;

    private Faculty faculty;
    private Department department;

    public Student() {}

    public Student(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String studentId, int course, String group, int enrollmentYear, StudyForm form, StudentStatus status, String facultyId, String specialty, String departmentId) throws InvalidValue {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        setStudentId(studentId);
        setCourse(course);
        setGroup(group);
        setEnrollmentYear(enrollmentYear);
        setStudyForm(form);
        setStudentStatus(status);
        this.facultyId = facultyId;
        setSpecialty(specialty);
        this.departmentId = departmentId;
    }

    public void setDepartmentId(String departmentId) throws InvalidValue{
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

        if (optionalDepartment.isEmpty()) {
            throw new InvalidValue("Кафедри з таким ID не знайдено.");
        }
        this.departmentId = departmentId;
    }

    public void setFacultyId(String facultyId) throws InvalidValue{
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

        if (optionalFaculty.isEmpty()) {
            throw new InvalidValue("Факультет з таким ID не знайдено.");
        }
        this.facultyId = facultyId;
    }

    public void setSpecialty(String specialty) throws InvalidValue{
        if (containsNonLetter(String.valueOf(specialty))) {
            throw new InvalidValue("Назва спеціальносі може містити лише літери");
        }
        this.specialty = specialty;
    }

    public void setStudentId(String studentId) throws InvalidValue {
        if (containsNonDigit(String.valueOf(studentId))) {
            throw new InvalidValue("Ідентифікатор може містити лише цифри");
        }
        this.studentId = studentId;
    }

    public void setCourse(int course) throws InvalidValue {
        if (containsNonDigit(String.valueOf(course)) || (course < 1 || course > 6)) {
            throw new InvalidValue("Курс має бути від 1 до 6");
        }
        this.course = course;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setEnrollmentYear(int year) throws InvalidValue {
        if (containsNonDigit(String.valueOf(year)) || (String.valueOf(year).length() != 4) || (year < 1900 || year > 2100)) {
            throw new InvalidValue("Некоректний рік");
        }
        this.enrollmentYear = year;
    }

    public void setStudyForm(StudyForm form) {
        this.form = form;
    }

    public void setStudentStatus(StudentStatus status) {
        this.status = status;
    }

    public StudentStatus getStudentStatus() {
    return status;
    }

    public String getFacultyName() {
        if (facultyId == null || facultyId.isEmpty()) return "---";
        return facultyRepository.findById(facultyId)
                .map(Faculty::getName)
                .orElse("ID не знайдено (" + facultyId + ")");
    }

    public String getDepartmentName() {
        if (departmentId == null || departmentId.isEmpty()) return "---";
        return departmentRepository.findById(departmentId)
                .map(Department::getName)
                .orElse("ID не знайдено (" + departmentId + ")");
    }

    @Override
    public String toString() {
        return "Student{" + "details=" + super.toString() + // Виклик toString() з Person
                ", studentId='" + studentId + '\'' + ", course=" + course + ", group='" + group + '\'' + ", enrollmentYear=" + enrollmentYear + ", form=" + form + ", status=" + status + '}';
    }


}