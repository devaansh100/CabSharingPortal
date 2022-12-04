import java.util.*;
public class BookingDatabase implements Database<Integer, Booking> {
    private HashMap<Integer, Booking> bookings = new HashMap<>();
    private int BookingNumber = 0;
    public ArrayList<Booking> getRelevantBookings(Booking booking){
        //Find the bookings with time +- 30 mins to booking, same src and dest, incomplete passenger count
        ArrayList<Booking> relevantBookings = new ArrayList<>();
        for(Map.Entry m : bookings.entrySet()){
            Booking b = (Booking) m.getValue();
            if(b.isRelevant(booking)){
                relevantBookings.add(b);
            }
        }
        return relevantBookings;
    }

    public HashMap<Integer, Booking> get() {
        return bookings;
    }

    public int getBookingNumber() {
        return BookingNumber++;
    }

    public void add(Integer bookingId, Booking booking){
        if (!bookings.containsValue(booking)) {
            bookings.put(bookingId, booking);
        }
    }
    public void remove(Integer bookingId){
        bookings.remove(bookingId);
    }

    public Booking query(Integer bookingId){
        return bookings.get(bookingId);
    }
}
