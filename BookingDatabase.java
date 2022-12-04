import java.util.*;
public class BookingDatabase implements Database<Integer, Booking> {
    private HashMap<Integer, Booking> bookings = new HashMap<>();
    private TreeSet<Booking> sortedBookings = new TreeSet<>(new Comparator<Booking>() {
        public int compare(Booking o1, Booking o2) {
            // First we will check for all factors other than the travel
            // The bookings that can be matched (but need to be checked only for timing) will hence be aggregated
            if(!o1.isCabSharing()) {
                return -1;
            }
            if(o1.isPaid()) {
                return -1;
            }
            if(o1.getEmptySeats() <= 0) {
                return -1;
            }
            // Now we will check for source and destination
            // If they do not match, they will be aggregated into units having same source-destinations
            if(!o1.getSrc().equals(o2.getSrc())) {
                return o1.getSrc().compareTo(o2.getSrc());
            }
            if(!o1.getDest().equals(o2.getDest())) {
                return o1.getDest().compareTo(o2.getDest());
            }
            // Now we will check for month, day, hours, mins exactly in this order
            if(o1.getMonth() != o2.getMonth()) {
                return o1.getMonth() - o2.getMonth();
            }
            if(o1.getDay() != o2.getDay()) {
                return o1.getDay() - o2.getDay();
            }
            if(o1.getHours() != o2.getHours()) {
                return o1.getHours() - o2.getHours();
            }
            return o1.getMins() - o2.getMins();
        }
    });
    private int BookingNumber = 0;
    public ArrayList<Booking> getRelevantBookings(Booking booking){
        //Find the bookings with time +- 30 mins to booking, same src and dest, incomplete passenger count
        ArrayList<Booking> relevantBookings = new ArrayList<>();

        Booking current = booking;
        boolean checkIfIsRelevant = true;
        // First check if bookings before this time can be shared
        while(checkIfIsRelevant) {
            current = sortedBookings.lower(current);
            if(current != null && current.isRelevant(booking)) {
                relevantBookings.add(current);
            } else {
                checkIfIsRelevant = false;
            }
        }
        checkIfIsRelevant = true;
        // Then check if bookings after this time can be shared
        current = booking;
        while(checkIfIsRelevant) {
            current = sortedBookings.higher(current);
            if(current != null && current.isRelevant(booking)) {
                relevantBookings.add(current);
            } else {
                checkIfIsRelevant = false;
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
            sortedBookings.add(booking);
        }
    }
    public void remove(Integer bookingId){
        if(!bookings.containsKey(bookingId))
            return;
        sortedBookings.remove(bookings.get(bookingId));
        bookings.remove(bookingId);
    }

    public Booking query(Integer bookingId){
        return bookings.get(bookingId);
    }
}
