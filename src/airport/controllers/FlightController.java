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
import java.util.List;

/**
 *
 * @author cisne
 */
public class FlightController {
    public static Response createFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival){
        try{
            if (!id.matches("^[A-Z]{3}[0-9]{3}")){
                return new Response("Flight ID must follow the format XXXYYY", Status.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
        return new Response("Person created succesfully",Status.CREATED);
    }
}
