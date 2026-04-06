package university.mapper;

import university.domain.Department;
import university.dto.DepartmentDto;

public class DepartmentMapper implements Mapper<Department, DepartmentDto> {
    @Override
    public DepartmentDto toDto(Department department) {
        if (department == null) return null;
        return new DepartmentDto(
                department.getID(),
                department.getName(),
                department.getFaculty(),
                department.getHead(),
                department.getLocation()
        );
    }

    @Override
    public Department toEntity(DepartmentDto dto) {
        if (dto == null) return null;
        return new Department(
                dto.getId(),
                dto.getName(),
                dto.getFaculty(),
                dto.getHead(),
                dto.getLocation()
        );
    }
}