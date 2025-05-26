/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.models.Location;
import airport.models.Plane;
import java.time.LocalDateTime;
import airport.models.Flight;
import airport.models.Passenger;
import airport.models.services.FlightService;
import airport.models.storage.FlightStorage;
import airport.models.storage.PassengerStorage;

/**
 *
 * @author cisne
 */
public class FlightController {

    public static Response createFlight(String id, Plane plane, Location scaleLocation, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        try {
            // Validación de formato de ID
            if (!id.matches("^[A-Z]{3}[0-9]{3}$")) {
                return new Response("Flight ID must follow the format XXX999", Status.BAD_REQUEST);
            }

            // Se valida para saber si ese vuelo ya existe o no, esto para prevenir duplicados
            FlightStorage flightStorage = FlightStorage.getInstance();
            if (flightStorage.exists(id)) {
                return new Response("Flight ID already exists", Status.CONFLICT);
            }

            // Validación de avión y locaciones
            if (plane == null) {
                return new Response("Plane cannot be null", Status.BAD_REQUEST);
            }
            if (departureLocation == null || arrivalLocation == null) {
                return new Response("Departure and arrival locations cannot be null", Status.BAD_REQUEST);
            }
            if (departureLocation.getAirportId().equals(arrivalLocation.getAirportId())) {
                return new Response("Departure and arrival locations must be different", Status.BAD_REQUEST);
            }

            // Validación de fecha
            if (departureDate == null || departureDate.isBefore(LocalDateTime.now())) {
                return new Response("Departure date must be in the future", Status.BAD_REQUEST);
            }

            // Validación de duración
            if (hoursDurationArrival < 0 || minutesDurationArrival < 0 || (hoursDurationArrival == 0 && minutesDurationArrival == 0)) {
                return new Response("Duration must be positive", Status.BAD_REQUEST);
            }

            // Se crea el vuelo despues de las validaciones 
            Flight flight;
            if (scaleLocation == null) {
                flight = new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival);
            } else{
                flight = new Flight(id, plane, scaleLocation, departureLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale);
            }
            boolean añadido = flightStorage.addFlight(flight);
            if (!añadido) {
                return new Response("Could not add flight", Status.INTERNAL_SERVER_ERROR);
            }

            return new Response("Flight created successfully", Status.CREATED, flight);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    public static void addPassengerToFlight(long passengerId, String flightId) {
        Passenger passenger = PassengerStorage.getInstance().getPassenger(passengerId);
        Flight flight = FlightStorage.getInstance().getFlight(flightId);

        if (passenger != null && flight != null) {
            if (!passenger.getFlights().contains(flight)) {
                passenger.addFlight(flight);
                flight.addPassenger(passenger);

                // Guardar cambios
                PassengerStorage.getInstance().saveAll(PassengerStorage.getInstance().getAll(), "src/data/passengers.json");
                FlightStorage.getInstance().saveAll(FlightStorage.getInstance().getAll(), "src/data/flights.json");
            } else {
                // Ya está agregado
                System.out.println("El pasajero ya está en el vuelo.");
            }
        }
    }
    public static boolean delayFlight(String flightId, int hours, int minutes) {
        Flight flight = FlightStorage.getInstance().getAll().stream()
                .filter(f -> f.getId().equals(flightId))
                .findFirst()
                .orElse(null);

        if (flight == null) {
            return false; // No encontró el vuelo
        }

        FlightService.delay(flight, hours, minutes);
        return true;
    }
}
