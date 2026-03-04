package university.repository;

public class StudentRepository extends PersonRepository{
    private static StudentRepository studentRepository;

    public static StudentRepository get(){
        if (studentRepository == null){
            studentRepository = new StudentRepository();
        }
        return studentRepository;
    }
}
