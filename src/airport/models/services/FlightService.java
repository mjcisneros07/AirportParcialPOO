/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.services;

import airport.models.Flight;
import airport.models.storage.FlightStorage;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cisne
 */
public class FlightService {

    public static LocalDateTime calculateArrivalDate(Flight flight) {
        return flight.getDepartureDate().plusHours(flight.getHoursDurationScale()).plusHours(flight.getHoursDurationArrival()).plusMinutes(flight.getMinutesDurationScale()).plusMinutes(flight.getMinutesDurationArrival());
    }

    public static void delay(Flight flight, int hours, int minutes) {
        flight.setDepartureDate(flight.getDepartureDate().plusHours(hours).plusMinutes(minutes));
    }

    public static List<Flight> getFlightsSortedByDepartureDate() {
        return FlightStorage.getInstance().getAll().stream().sorted(Comparator.comparing(Flight::getDepartureDate)).collect(Collectors.toList());
    }
    public static List<Flight> getFlightsSortedById(){
         return FlightStorage.getInstance().getAll().stream().sorted((a, b) -> a.getId().compareTo(b.getId())).collect(Collectors.toList());
    }

}
