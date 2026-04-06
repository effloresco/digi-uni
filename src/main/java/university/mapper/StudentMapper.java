package university.mapper;

import university.domain.Student;
import university.dto.StudentDto;

public class StudentMapper implements Mapper<Student, StudentDto>{
    @Override
    public StudentDto toDto(Student student) {
        if (student == null) return null;
        return new StudentDto(
                student.getID(),
                student.getLastName(),
                student.getFirstName(),
                student.getMiddleName(),
                student.getBirthDate(),
                student.getEmail(),
                student.getPhone(),
                student.getStudentId(),
                student.getCourse(),
                student.getGroup(),
                student.getEnrollmentYear(),
                student.getForm(),
                student.getStatus()
        );
    }

    @Override
    public Student toEntity(StudentDto dto) {
        if (dto == null) return null;
        return new Student(
                dto.getId(),
                dto.getLastName(),
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getBirthDate(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getStudentId(),
                dto.getCourse(),
                dto.getGroup(),
                dto.getEnrollmentYear(),
                dto.getForm(),
                dto.getStatus()
        );
    }
}
