import com.github.javafaker.Faker;
public class DummyData {
    public static void fillDummyData() throws UserAlreadyExistsException, UsernameDoesNotExistException {
        Faker data = new Faker();
        String name, email, phone, id;
        for(int i = 0; i < 100; i++){
            name = data.name().firstName();
            id = name + data.bothify("##");
            email = id + "@" + data.company().name() + ".com";
            phone = data.phoneNumber().cellPhone();
            if(i == 0) {
                System.out.println("Name: " + name);
                System.out.println("Phone: " + phone);
                System.out.println("Email: " + email);
                System.out.println("Username: " + id);
            }
            StudentDb.addDetails(id, name, phone, email);
            StudentDb.addToPasswords(id, "");
            Booking b = new Booking(Origin.PILANI, Destination.DELHI, 11, 12, 12, 30, Car.NON_AC_SUV, true);
            Student s = StudentDb.queryDetails(id);
            b.addPassengers(s);
            s.addBooking(b);
            BookingDb.addBooking(b);
        }
//        Origin src, Destination dest, int month, int day, int hours, int mins, Car car, boolean carrier) {
//        for(int i = 0; i < 50; i++){
//            BookingDb.addBooking(new Booking(Origin.PILANI, Destination.DELHI, 11, 12, 12, 30, Car.NON_AC_SUV, true));
//        }
    }
}
