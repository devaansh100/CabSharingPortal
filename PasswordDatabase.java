import java.util.*;
public class PasswordDatabase implements Database<String, String> {
    HashMap<String, String> passwords = new HashMap<>();

    public String query(String username) throws UsernameDoesNotExistException{
        String storedPassword = passwords.get(username);
        if(storedPassword == null){
            throw new UsernameDoesNotExistException("This user does not exist"); //TODO: Define this exception
        }
        else{
            return storedPassword;
        }
    }

    public void add(String username, String password) throws UserAlreadyExistsException{
        if(passwords.containsKey(username)){
            throw new UserAlreadyExistsException("Attempting to change the password of an existing user"); //TODO: Define this exception
        }
        else {
            passwords.put(username, password);
        }
    }

    public HashMap<String, String> get() {
        return passwords;
    }

    public void remove(String username){
        passwords.remove(username);
    }
}
