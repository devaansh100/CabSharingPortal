import com.github.javafaker.Faker;

import java.sql.Array;
import java.util.*;
public class DummyData {
    private static ArrayList<Integer> multipleUniqueNumbers(int min, int max, int num) {
        ArrayList<Integer> out = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=min; i<=max; i++) list.add(i);
        Collections.shuffle(list);
        for (int i=0; i<num; i++) {
            out.add(list.get(i));
        }
        return out;
    }
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static void fillDummyData() throws UserAlreadyExistsException, UsernameDoesNotExistException {
        Faker data = new Faker();
        String name, email, phone, id, password;
        HashMap<Integer, Origin> origins = new HashMap<>();
        origins.put(1, Origin.PILANI);
        origins.put(2, Origin.DELHI);
        origins.put(3, Origin.JAIPUR);

        HashMap<Integer, Destination> dests = new HashMap<>();
        dests.put(1, Destination.PILANI);
        dests.put(2, Destination.DELHI);
        dests.put(3, Destination.JAIPUR);

        HashMap<Integer, Car> cars = new HashMap<>();
        cars.put(1, Car.NON_AC_SUV);
        cars.put(2, Car.AC_SUV);
        cars.put(3, Car.NON_AC_SEDAN);
        cars.put(4, Car.AC_SEDAN);

        ArrayList<String> usernames = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            name = data.name().firstName();
            id = name + data.bothify("##");
            email = name + "@" + data.company().name() + ".com";
            phone = data.phoneNumber().cellPhone();
            password = data.bothify("###???");
            System.out.println("Username: " + id);
            System.out.println("Password: " + password);
            System.out.println("--------------------------");
            usernames.add(id);
            Db.StudentDb.add(id, new Student(id, name, phone, email));
            Db.PasswordDb.add(id, password);
            ArrayList<Integer> locs = multipleUniqueNumbers(1, 3, 2);
            ArrayList<Integer> car = multipleUniqueNumbers(1, 4, 1);
            Booking b = new Booking(origins.get(locs.get(0)), dests.get(locs.get(1)), getRandomNumber(11, 12), getRandomNumber(1, 2), getRandomNumber(11, 12), getRandomNumber(15, 45), cars.get(car.get(0)), getRandomNumber(0, 1) == 1);
            Student s = Db.StudentDb.query(id);
            b.addPassengers(s);
            s.addBooking(b);
            for(int j = 1; j <= 3; j++) {
                if (getRandomNumber(1, 4) == 2) {
                    Student newS = Db.StudentDb.query(usernames.get(getRandomNumber(0, usernames.size() - 2)));
                    b.addPassengers(newS);
                    newS.addBooking(b);
                }
            }
            Db.BookingDb.add(b.getBookingId(), b);
        }
    }
}
