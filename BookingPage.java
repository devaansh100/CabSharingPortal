import java.util.*;
public class BookingPage implements BookingFeatures {
    public void makeBooking(Student actor) throws IllegalInputException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose origin(P/J/D)");
        Origin origin;
        String src = sc.nextLine();
        switch(src){
            case "P":
                origin = Origin.PILANI;
                break;

            case "J":
                origin = Origin.JAIPUR;
                break;

            case "D":
                origin = Origin.DELHI;
                break;

            default:
                throw new IllegalLocationException("Location can only be P/J/D and destination and source must be different");

        }
        System.out.println("Choose destination(P/J/D)");
        Destination dest;
        String to = sc.nextLine();
        if(src == to)
            throw new IllegalLocationException("Location can only be P/J/D and destination and source must be different");
        switch(to){
            case "P":
                dest = Destination.PILANI;
                break;

            case "J":
                dest = Destination.JAIPUR;
                break;

            case "D":
                dest = Destination.DELHI;
                break;

            default:
                throw new IllegalLocationException("Location can only be P/J/D and destination and source must be different");
        }
        System.out.println("Choose car: 1. AC Sedan, 2. Non-AC Sedan, 3. AC SUV, 4. Non-AC SUV");
        Car car;
        int ch = Integer.parseInt(sc.nextLine());
        switch(ch){
            case 1:
                car = Car.AC_SEDAN;
                break;
            case 2:
                car = Car.NON_AC_SEDAN;
                break;
            case 3:
                car = Car.AC_SUV;
                break;
            case 4:
                car = Car.NON_AC_SUV;
                break;
            default:
                throw new CarNotAvailableException("Chosen car is unavailable");
        }
        //TODO: Take better date and time input and throw relevant Exceptions
        System.out.println("Enter month(1-12)");
        int month = Integer.parseInt(sc.nextLine());;
        if(month < 1 || month > 12)
            throw new IllegalInputException("Month input has to be between 1 and 12.");

        System.out.println("Enter day(1-30)");
        int day = Integer.parseInt(sc.nextLine());
        if(day < 1 || day > 30)
            throw new IllegalInputException("Day input has to be between 1 and 30.");

        System.out.println("Enter time(HH:MM)");
        String time = sc.nextLine();
        String[] arr = time.split(":");
        if(arr.length != 2)
            throw new IllegalInputException("Incorrect input format for time.");

        int hours = Integer.parseInt(arr[0]);
        int mins = Integer.parseInt(arr[1]);
        System.out.println("Need a luggage carrier?(0/1)");
        boolean carrier = Integer.parseInt(sc.nextLine()) == 1;
        System.out.println("Opt In for Cab sharing?(0/1)");
        Booking booking = new Booking(origin, dest, month, day, hours, mins, car, carrier);
        if(Integer.parseInt(sc.nextLine()) == 1){
            booking = makeCabSharingBooking(booking);
        }
        else{
            booking.noCabSharing();
        }
        booking.addPassengers(actor);
        actor.addBooking(booking);
        BookingDb.addBooking(booking);
        System.out.println("Booking successful!");
    }
    public Booking makeCabSharingBooking(Booking currentBooking){
        Scanner sc = new Scanner(System.in);
        ArrayList<Booking> bookings = BookingDb.getRelevantBookings(currentBooking);
        if(bookings.size() == 0){
            System.out.println("No cabs to share!");
            return currentBooking;
        }
        System.out.println("Choose the serial number of the booking you want to join");
        int idx = 1;
        for(Booking b : bookings){
            System.out.println(idx++ + ") " + b.toString());
        }
        int ch = Integer.parseInt(sc.nextLine());
        currentBooking = bookings.get(ch - 1);
        return currentBooking;

    }
    public void viewBookings(Student actor){
        Scanner sc = new Scanner(System.in);
        ArrayList<Booking> bookings = new ArrayList<>(actor.getBookings());
        int idx = 1;
        for(Booking b : bookings){
            System.out.println(idx++ + ") " + b.toString());
        }
        if(bookings.size() > 0) {
            System.out.println("Would you like to cancel a booking?(0/1)");
            int ch = Integer.parseInt(sc.nextLine());
            if (ch == 1) {
                System.out.println("Choose serial number of the booking to cancel");
                int cancel = Integer.parseInt(sc.nextLine());
                Booking booking = bookings.get(cancel - 1);
                booking.removePassengers(actor);
                actor.removeBooking(booking);
                System.out.println("Successfully cancelled booking!");
            }
        }
        else{
            System.out.println("No bookings yet!");
        }
    }

    public void checkNotifications(Student actor) throws IllegalActionOnNotification{
        Scanner sc = new Scanner(System.in);
        ArrayList<Notification> notifications = actor.getNotifications();
        int idx = 1;
        for(Notification n : notifications){
            System.out.println(idx++ + ") " + n.toString());
        }
        if(notifications.size() > 0) {
            System.out.println("Choose 1 to interact with a notification(Accept/Reject a trip), 2 to delete a notification, 3 to go back");
            int ch = Integer.parseInt(sc.nextLine());
            Notification notification;
            switch (ch) {
                case 1:
                    System.out.println("Choose serial number of the notification to accept/reject a trip");
                    int n = Integer.parseInt(sc.nextLine());
                    notification = notifications.get(n - 1);
                    if (!notification.isInteractable()) {
                        throw new IllegalActionOnNotification("Invalid notification - nothing to interact!");
                    }
                    Booking b = notification.getBooking();
                    System.out.println("1 to accept, 0 to reject");
                    boolean accept = Integer.parseInt(sc.nextLine()) == 1;
                    if (accept) {
                        b.acceptPassenger(actor);
                        System.out.println("Trip was accepted!");
                    } else {
                        b.rejectPassenger(actor);
                        System.out.println("Trip was rejected!");
                    }
                    actor.removeNotifications(notification); // Removing current interacted notification
                    ArrayList<Notification> notificationsToRemove = new ArrayList<>(); // Removing notifications which required the same interaction
                    for(Notification notif : actor.getNotifications()){
                        if(notif.getBooking() == b){
                            notificationsToRemove.add(notif);
                        }
                    }
                    for(Notification notif : notificationsToRemove){
                        actor.removeNotifications(notif);
                    }
                    break;
                case 2:
                    System.out.println("Choose serial number of the notification to delete");
                    int cancel = Integer.parseInt(sc.nextLine());
                    notification = notifications.get(cancel - 1);
                    if (!notification.isInteractable())
                        actor.removeNotifications(notification);
                    else
                        throw new IllegalActionOnNotification("Cannot delete notification since you have not interacted with it");
                    break;
            }
        }
        else{
            System.out.println("No notifications to show!");
        }
    }
}
