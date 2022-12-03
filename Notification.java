import java.time.LocalDateTime;
public class Notification {
    String msg;
    LocalDateTime time;

    public Booking getBooking() {
        return booking;
    }

    Booking booking;
    public Notification(String msg) {
        this.msg = msg;
        this.time = LocalDateTime.now();
    }

    public Notification(String msg, Booking b){
        this.msg = msg;
        this.time = LocalDateTime.now();
        this.booking = b;

    }
    public boolean isInteractable(){
        return this.getBooking() != null;
    }
    @Override
    public String toString() {
        return this.getTime() + ": " + this.getMsg();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }
}
