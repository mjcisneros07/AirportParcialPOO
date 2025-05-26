/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.models.Flight;
import airport.models.Location;
import airport.models.Passenger;
import airport.models.Plane;
import airport.models.services.FlightService;
import airport.models.services.LocationService;
import airport.models.services.PassengerService;
import airport.models.services.PlaneService;
import airport.models.storage.FlightStorage;
import airport.models.storage.LocationStorage;
import airport.models.storage.PassengerStorage;
import airport.models.storage.PlaneStorage;
import airport.views.AirportFrame;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cisne
 */
public class AirportController {

    private FlightStorage flightStorage;
    private PlaneStorage planeStorage;
    private AirportFrame view;
    private LocationStorage locationStorage;
    private PassengerStorage passengerStorage;

    public AirportController(AirportFrame view) {
        this.view = view;
        this.flightStorage = FlightStorage.getInstance();
        this.planeStorage = PlaneStorage.getInstance();
        this.locationStorage = LocationStorage.getInstance();
        this.passengerStorage = PassengerStorage.getInstance();
    }

    public static List<Passenger> getPassengersOrderedById() {
        return PassengerService.getAllPassengersSortedById();
    }

    public static List<Plane> getPlanesOrderedById() {
        return PlaneService.getAllPlanesSortedById();
    }

    public static List<Location> getLocationsOrderedById() {
        return LocationService.getAllLocationsSortedById();
    }

    public static List<Flight> getFlightsOrderedByDepartureDate() {
        return FlightService.getFlightsSortedByDepartureDate();
    }

    public static List<Flight> getFlightsOrderedById() {
        return FlightService.getFlightsSortedById();
    }

    public static List<Flight> getFlightsOfPassenger(long passengerId) {
        Passenger p = PassengerService.findById(passengerId);
        return (p != null) ? p.getFlights() : new ArrayList<>();
    }
}
