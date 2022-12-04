import java.util.HashMap;

public class StudentDatabase implements Database<String, Student>{
    HashMap<String, Student> details = new HashMap<>();

    public Student query(String username) throws UsernameDoesNotExistException{
        Student studentInfo = details.get(username);
        if(studentInfo == null){
            throw new UsernameDoesNotExistException("This user does not exist"); //TODO: Define this exception
        }
        else{
            return studentInfo;
        }
    }

    public HashMap<String, Student> get() {
        return details;
    }

    public void add(String username, Student student) throws UserAlreadyExistsException{
        if(details.containsKey(username)){
            throw new UserAlreadyExistsException("Attempting to change the details of an existing user");
        }
        else {
            details.put(username, student);
        }
    }

    public void remove(String username){
        details.remove(username);
    }

}
