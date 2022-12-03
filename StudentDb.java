import java.util.HashMap;

public class StudentDb {
    static HashMap<String, String> passwords = new HashMap<>(); //TODO: Fill with dummy values
    static HashMap<String, Student> details = new HashMap<>();
    //username = IDNo
    public static String queryPassword(String username) throws UsernameDoesNotExistException{
        String storedPassword = passwords.get(username);
        if(storedPassword == null){
            throw new UsernameDoesNotExistException("This user does not exist"); //TODO: Define this exception
        }
        else{
            return storedPassword;
        }
    }

    public static Student queryDetails(String username) throws UsernameDoesNotExistException{
        Student studentInfo = details.get(username);
        if(studentInfo == null){
            throw new UsernameDoesNotExistException("This user does not exist"); //TODO: Define this exception
        }
        else{
            return studentInfo;
        }
    }

    public static HashMap<String, Student> getDetails() {
        return details;
    }

    public static void addDetails(String username, String name, String contactNumber, String email) throws UserAlreadyExistsException{
        if(details.containsKey(username)){
            throw new UserAlreadyExistsException("Attempting to change the details of an existing user"); //TODO: Define this exception
        }
        else {
            Student student = new Student(username, name, contactNumber, email);
            details.put(username, student);
        }
    }

    public static void addToPasswords(String username, String password) throws UserAlreadyExistsException{
        if(passwords.containsKey(username)){
            throw new UserAlreadyExistsException("Attempting to change the password of an existing user"); //TODO: Define this exception
        }
        else {
            passwords.put(username, password);
        }
    }
}
