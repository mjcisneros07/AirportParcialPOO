/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.services;

import airport.models.Passenger;
import airport.models.storage.PassengerStorage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cisne
 */
public class PassengerService {

    public static List<Passenger> getAllPassengersSortedById() {
        return PassengerStorage.getInstance().getAll().stream().sorted(Comparator.comparingLong(Passenger::getId)).collect(Collectors.toList());
    }

    public static Passenger findById(long id) {
        return PassengerStorage.getInstance().getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
    
    public static void updatePassenger(Passenger updatedPassenger) {
        List<Passenger> passengers = PassengerStorage.getInstance().getAll();

        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getId() == updatedPassenger.getId()) {
                passengers.set(i, updatedPassenger);
                break;
            }
        }

        PassengerStorage.getInstance().saveAll(passengers, "src/data/passengers.json");
    }
}
