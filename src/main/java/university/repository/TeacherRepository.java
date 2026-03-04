package university.repository;

public class TeacherRepository extends PersonRepository{
    private static TeacherRepository teacherRepository;

    public static TeacherRepository get(){
        if (teacherRepository == null){
            teacherRepository = new TeacherRepository();
        }
        return teacherRepository;
    }
}
