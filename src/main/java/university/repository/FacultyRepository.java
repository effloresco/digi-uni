package university.repository;
import university.domain.Faculty;

public class FacultyRepository extends Repository<Faculty, String>{
    private static FacultyRepository facultyRepository;

    public static FacultyRepository get(){
        if (facultyRepository == null){
            facultyRepository = new FacultyRepository();
        }
        return facultyRepository;
    }
}
