package university.domain;

import university.exceptions.InvalidValue;
import static university.service.Utils.*;

public class Student extends Person {
    public enum StudyForm {BUDGET, CONTRACT}
    public enum StudentStatus {STUDYING, ACADEMIC_LEAVE, EXPELLED}

    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private StudyForm form;
    private StudentStatus status;

    public Student() {
    }

    public String getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public void setStudentId(String studentId) throws InvalidValue {
        if (containsNonDigit(String.valueOf(studentId))) {
            throw new InvalidValue("Ідентифікатор може містити лише цифри");
        }
        this.studentId = studentId;
    }

    public void setCourse(int course) throws InvalidValue{
        if (containsNonDigit(String.valueOf(course))||(course < 1 || course > 6)) {
            throw new InvalidValue("Курс має бути від 1 до 6");
        }
    }
    public void setGroup(String group){
        this.group = group;
    }
    public void setEnrollmentYear(int year)throws  InvalidValue{
        if (containsNonDigit(String.valueOf(year))||String.valueOf(year).length()!=4) {
            throw new InvalidValue("Некоректний рік");
        }
        this.enrollmentYear = year;
    }
    public void setStudyForm(StudyForm form){
        this.form = form;
    }
    public void setStudentStatus(StudentStatus status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "details=" + super.toString() + // Виклик toString() з Person
                ", studentId='" + studentId + '\'' +
                ", course=" + course +
                ", group='" + group + '\'' +
                ", enrollmentYear=" + enrollmentYear +
                ", form=" + form +
                ", status=" + status +
                '}';
    }


}