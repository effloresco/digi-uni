package university;

import java.time.LocalDate;

enum StudyForm {BUDGET, CONTRACT}

enum StudentStatus {STUDYING, ACADEMIC_LEAVE, EXPELLED}

class Student extends Person {
    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private StudyForm studyForm;
    private StudentStatus status;

    public Student(String id, String lastName, String firstName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   String studentId, int course, String group, int enrollmentYear,
                   StudyForm studyForm) {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        this.studentId = studentId;
        setCourse(course);
        this.group = group;
        this.enrollmentYear = enrollmentYear;
        this.studyForm = studyForm;
        this.status = StudentStatus.STUDYING;
    }

    public String getStudentId() {
        return studentId;
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