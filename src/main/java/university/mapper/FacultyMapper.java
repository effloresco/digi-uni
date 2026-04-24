package university.mapper;

import university.domain.Faculty;
import university.dto.FacultyDto;

public class FacultyMapper implements Mapper<Faculty, FacultyDto> {
    @Override
    public FacultyDto toDto(Faculty faculty) {
        if (faculty == null) return null;
        return new FacultyDto(
                faculty.getID(),
                faculty.getName(),
                faculty.getShortName(),
                faculty.getDeanId(),
                faculty.getContacts()
        );
    }

    @Override
    public Faculty toEntity(FacultyDto dto) {
        if (dto == null) return null;
        return new Faculty(
                dto.getCode(),
                dto.getName(),
                dto.getShortName(),
                dto.getDeanId(),
                dto.getContacts()
        );
    }
}