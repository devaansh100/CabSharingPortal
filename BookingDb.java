import java.util.*;
public class BookingDb {
    private static HashMap<Integer, Booking> bookings = new HashMap<>();
    private static int BookingNumber = 0;
    public static ArrayList<Booking> getRelevantBookings(Booking booking){
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

    public static HashMap<Integer, Booking> getBookings() {
        return bookings;
    }

    public static int getBookingNumber() {
        return BookingNumber++;
    }

    public static void addBooking(Booking booking){
        if (!bookings.containsValue(booking)) {
            bookings.put(booking.getBookingId(), booking);
        }
    }
    public static void removeBooking(Booking booking){
        bookings.remove(booking.getBookingId());
    }
}
