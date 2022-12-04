import java.lang.*;
import java.util.*;
public class Driver {
    public static void main(String[] args) {
        int input = -1;
        Scanner sc = new Scanner(System.in);
        try {
            DummyData.fillDummyData();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        while (input != 2) {
            System.out.println("Choose 1 for login, 2 to close application");
            input = Integer.parseInt(sc.nextLine());
            if (input == 1) { //TODO: Decouple this with the stdin - so multiple users can login at the same time
                Application app = new Application();
                app.start();
                try {
                    app.join();
                }
                catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}