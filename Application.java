import java.util.*;
public class Application extends Thread{
    private LoginPage login;
    private BookingPage bookingPage;
    private AdminPage adminPage;
    private Student student;
    public Application() {
        login = new LoginPage();
        bookingPage = new BookingPage();
        adminPage = new AdminPage();
    }
    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to register, 2 to login, 3 to admin login");
        boolean loginSuccess = false;
        int ch = Integer.parseInt(sc.nextLine());
        switch(ch){
            case 1:
                loginSuccess = login.register();
                break;

            case 2:
                loginSuccess = login.login();
                break;

            case 3:
                loginSuccess = login.admin();
                break;
        }
        ch = -1;
        if(loginSuccess){
            if(login.isAdmin()){
                while (ch != 4) {
                    System.out.println("Choose 1 to view all bookings, 2 to view all student details, 3 to send payments, 4 to logout");
                    ch = Integer.parseInt(sc.nextLine());
                    switch(ch){
                        case 1:
                            adminPage.viewAllBookings();
                            break;
                        case 2:
                            adminPage.viewAllStudentDetails();
                            break;
                        case 3:
                            adminPage.sendPayments();
                            break;
                    }
                }
            }
            else {
                student = login.getStudent();
                System.out.println("Welcome " + student.toString());
                while (ch != 4) {
                    System.out.println("Choose 1 to make a booking, 2 to view your bookings, 3 to view your " +
                            "notifications(" + student.getNotifications().size() + "), 4 to logout");
                    ch = Integer.parseInt(sc.nextLine());
                    switch (ch) {
                        case 1:
                            try {
                                bookingPage.makeBooking(student);
                            }
                            catch(IllegalLocationException | CarNotAvailableException e){
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        case 2:
                            bookingPage.viewBookings(student);
                            break;
                        case 3:
                            bookingPage.checkNotifications(student);
                            break;
                    }
                }
            }
            login.logout();
            this.interrupt();
        }
    }
}
