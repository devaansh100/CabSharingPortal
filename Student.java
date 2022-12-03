import java.util.*;
public class Student {
    private String name, id, contactNumber, email;
    private ArrayList<Notification> notifications;
    private HashSet<Booking> bookings;
    private int dues;
    public Student(String name, String id, String contactNumber, String email) {
        this.name = name;
        this.id = id;
        this.contactNumber = contactNumber;
        this.email = email;
        this.notifications = new ArrayList<>();
        this.bookings = new HashSet<>();
        this.dues = 0;
    }

    @Override
    public String toString() {
        return   "\nName: " + name +
                        "\nID: " + id +
                        "\nContact Number: " + contactNumber +
                        "\nEmail: " + email +
                        "\nDues: " + dues;

    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addNotifications(Notification notification) {
        this.notifications.add(notification);
    }

    public void removeNotifications(Notification notification) {
        this.notifications.remove(notification);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    public HashSet<Booking> getBookings() {
        return bookings;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void charge(int amount){
        dues += amount;
    }
}
