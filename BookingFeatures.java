public interface BookingFeatures {
    public void makeBooking(Student actor) throws IllegalInputException;
    public Booking makeCabSharingBooking(Booking currentBooking);
    public void viewBookings(Student actor);
    public void checkNotifications(Student actor) throws IllegalActionOnNotification;
}
