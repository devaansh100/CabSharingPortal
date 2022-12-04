import java.util.*;
public class Booking {
    private Origin src;
    private Destination dest;
    private int month, day, hours, mins, emptySeats;
    private Car car;
    private boolean carrier;
    ArrayList<Student> passengers;
    ArrayList<Boolean> accepted;
    private int bookingId;
    private boolean status, paid, cabSharing;

    public Booking(Origin src, Destination dest, int month, int day, int hours, int mins, Car car, boolean carrier) {
        this.src = src;
        this.dest = dest;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.mins = mins;
        this.car = car;
        this.carrier = carrier;
        this.passengers = new ArrayList<Student>();
        this.accepted = new ArrayList<Boolean>();
        this.emptySeats = 3;
        this.status = false;
        this.paid = false;
        this.cabSharing = true;
        this.bookingId = Db.BookingDb.getBookingNumber();
    }
    public String toString() {
        String repr = bookingId + ": Trip from " + src + " to " + dest + " in " + car + " on " + day + "/" + month + " at " + hours + ":" + mins +
                ". Passengers are ";
        for(Student s : passengers){
            String student_repr = s.getName() + "(" + s.getId() + ")";
            repr = repr + student_repr + ", ";
        }
        String status = this.status ? "CONFIRMED" : "PROPOSED";
        String paid = this.paid ? "PAID - NO REFUND" : "NOT PAID";
        repr = repr.substring(0, repr.length() - 1) + ". | " + status + " | " + paid;
        return repr;
    }

    public Origin getSrc() {
        return src;
    }
    public void noCabSharing(){
        this.cabSharing = false;
    }

    public Destination getDest() {
        return dest;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Car getCar() {
        return car;
    }

    public boolean getCarrier() {
        return carrier;
    }

    public int getHours() {
        return hours;
    }

    public int getMins() {
        return mins;
    }

    public ArrayList<Student> getPassengers() {
        return passengers;
    }

    public int getEmptySeats() {
        return emptySeats;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void addPassengers(Student passenger) {
        if(!this.passengers.contains(passenger) && this.emptySeats > 0) {
            this.passengers.add(passenger);
            this.accepted.add(true);
            this.emptySeats--;
            int idx = 0;
            for (Student s : this.passengers) {
                if (s != passenger) {
                    s.addNotifications(new Notification(this.bookingId + " has a new passenger: " + passenger.getName()
                            + ", " + passenger.getId() + ". Reach out to them at " + passenger.getEmail() + " or " + passenger.getContactNumber(), this));
                    this.accepted.set(idx, false);
                }
            }
            this.updateStatus();
        }
    }

    public void acceptPassenger(Student passenger){
        this.accepted.set(this.passengers.indexOf(passenger), true);
        for(Student s : getPassengers()){
            if(s != passenger){
                s.addNotifications(new Notification("Trip " + getBookingId() + ": " + s.getName() + " has accepted the trip."));
            }
        }
        this.updateStatus();
    }

    public void rejectPassenger(Student passenger){
        this.removePassengers(passenger);
        passenger.removeBooking(this);
    }

    private void updateStatus(){
        this.status = true;
        for(boolean a : this.accepted){
            this.status = this.status && a;
        }
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isRelevant(Booking b){
        return this.cabSharing && this.month == b.getMonth() && this.day == b.getDay() && this.src == b.getSrc()
                && this.dest == b.getDest() && b.getEmptySeats() > 0 && Math.abs(this.hours * 60 + this.mins - b.getHours() * 60
                - b.getMins()) <= 30 && !b.isPaid();
    }

    public boolean isStatus() {
        return status;
    }

    public void removePassengers(Student passenger) {
        this.accepted.remove(this.passengers.indexOf(passenger));
        this.passengers.remove(passenger);
        this.emptySeats++;
        for(Student s : this.passengers){
            s.addNotifications(new Notification("Trip " + getBookingId() + ": " + s.getName() + " has rejected the trip."));
        }
        if(this.passengers.size() == 0){
            Db.BookingDb.remove(this.getBookingId());
        }
        this.updateStatus();
    }

    public int getPrice(){ //TODO: Change to some calculation based on other parameters
        return 4500;
    }

    public void setPaid(){
        this.paid = true;
    }

    public boolean isCabSharing() {
        return cabSharing;
    }
}
