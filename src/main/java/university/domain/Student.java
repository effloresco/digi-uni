package university.domain;
import java.time.LocalDate;

public class Student extends Person {

    public enum StudyForm {BUDGET, CONTRACT}
    public enum StudentStatus {STUDYING, ACADEMIC_LEAVE, EXPELLED}

    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private StudyForm form;
    private StudentStatus status;

    public Student(String id, String lastName, String firstName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   String studentId, int course, String group, int enrollmentYear,
                   StudentStatus status, StudyForm form) {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        this.studentId = studentId;
        setCourse(course);
        this.group = group;
        this.enrollmentYear = enrollmentYear;
        this.form = form;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getGroup() {
        return group;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        if (course < 1 || course > 6) throw new IllegalArgumentException("Курс має бути від 1 до 6");
        this.course = course;
    }

}