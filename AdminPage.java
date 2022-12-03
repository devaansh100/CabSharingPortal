import java.util.*;
public class AdminPage {
    public void viewAllStudentDetails(){
        HashMap<String, Student> details = StudentDb.getDetails();
        if(details.size() == 0){
            System.out.println("No students to show!");
            return;
        }
        for(Map.Entry<String, Student> m : details.entrySet()){
            System.out.println(m.getKey());
            System.out.println(m.getValue().toString());
            System.out.println("===============================");
        }
    }

    public void viewAllBookings(){
        HashMap<Integer, Booking> bookings = BookingDb.getBookings();
        if(bookings.size() == 0){
            System.out.println("No bookings to show!");
            return;
        }
        for(Map.Entry m : bookings.entrySet()){
            Booking b = (Booking) m.getValue();
            System.out.println(b.toString());
            System.out.println("===============================");
        }
    }

    private void sendPaymentForOneBooking(Booking b){
        if (b.isStatus()){
            for(Student s : b.getPassengers()){
                int amount = b.getPrice() / (3 - b.getEmptySeats());
                s.charge(amount);
                s.addNotifications(new Notification("Charging " + amount + " for Trip ID " + b.getBookingId()));
                b.setPaid();
            }
        }
    }
    public void sendPayments(){
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Booking> bookings = BookingDb.getBookings();
        if(bookings.size() == 0){
            System.out.println("No confirmed bookings yet!");
            return;
        }
        ArrayList<Booking> confirmedBookings = new ArrayList<>();
        int idx = 0;
        for(Map.Entry m : bookings.entrySet()){
            Booking b = (Booking) m.getValue();
            if(b.isStatus()) {
                confirmedBookings.add(b);
                System.out.println(idx++ + ") " + b.toString());
                System.out.println("===============================");
            }
        }
        System.out.println("Give a space separated list of serial numbers to send payments to those bookings, put -1 to send to all bookings");
        String ch = sc.nextLine();
        String arr[] = ch.split(" ");
        for(String num : arr){
            idx = Integer.parseInt(num);
            if(idx == -1){
                for(Booking b : confirmedBookings){
                    this.sendPaymentForOneBooking(b);
                }
                break;
            }
            else{
                this.sendPaymentForOneBooking(confirmedBookings.get(idx));
            }
        }
        System.out.println("Successfully sent the required payments!");
    }
}
