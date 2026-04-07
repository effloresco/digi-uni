package university.dto;

import lombok.Getter;
import university.domain.Department;
import university.domain.Faculty;
import university.domain.Person;
import university.domain.User;

@Getter
public class ServiceDto {
    private final int personCounter;
    private final int facultyCounter;
    private final int departmentCounter;
    private final int userCounter;
    public ServiceDto() {
        this.personCounter = Person.getIdCounter();
        this.facultyCounter = Faculty.getIdCounter();
        this.departmentCounter = Department.getIdCounter();
        this.userCounter = User.getIdCounter();
    }
}
