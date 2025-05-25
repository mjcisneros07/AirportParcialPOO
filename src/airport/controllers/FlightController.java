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
import airport.models.storage.FlightStorage;
import java.util.List;

/**
 *
 * @author cisne
 */
public class FlightController {
    public static Response createFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
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
            Flight flight = new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival);
            boolean añadido = flightStorage.addFlight(flight);
            if (!añadido) {
                return new Response("Could not add flight", Status.INTERNAL_SERVER_ERROR);
            }

            return new Response("Flight created successfully", Status.CREATED, flight);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
