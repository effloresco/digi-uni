package university.mapper;

import university.domain.Teacher;
import university.dto.StudentDto;
import university.dto.TeacherDto;

public class TeacherMapper implements Mapper<Teacher, TeacherDto> {
    @Override
    public TeacherDto toDto(Teacher teacher) {
        if (teacher == null) return null;
        return new TeacherDto(
                teacher.getID(),
                teacher.getLastName(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getBirthDate(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getPosition(),
                teacher.getDegree(),
                teacher.getTitle(),
                teacher.getHireDate(),
                teacher.getRate()
        );
    }

    @Override
    public Teacher toEntity(TeacherDto dto) {
        if (dto == null) return null;
        return new Teacher(
                dto.getId(),
                dto.getLastName(),
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getBirthDate(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getPosition(),
                dto.getDegree(),
                dto.getTitle(),
                dto.getHireDate(),
                dto.getRate()
        );
    }
}