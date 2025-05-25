/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.models.Passenger;
import airport.models.storage.PassengerStorage;

/**
 *
 * @author apont
 */
public class PassengerController {

    public static Response createPassenger(Passenger passenger) {
        try {
            if (passenger == null) {
                return new Response("Passenger cannot be null", Status.BAD_REQUEST);
            }
            PassengerStorage passengerStorage = PassengerStorage.getInstance();
            if (passengerStorage.exists(passenger.getId())) {
                return new Response("Passenger already exists", Status.CONFLICT);
            }
            boolean añadido = passengerStorage.addPassenger(passenger);
            if (!añadido) {
                return new Response("Could not add passenger", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Passenger created successfully", Status.CREATED, passenger);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPassenger(long id) {
        try {
            PassengerStorage passengerStorage = PassengerStorage.getInstance();
            Passenger p = passengerStorage.getPassenger(id);
            if (p == null) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            return new Response("Passenger found", Status.OK, p);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response removePassenger(long id) {
        try {
            PassengerStorage passengerStorage = PassengerStorage.getInstance();
            if (!passengerStorage.exists(id)) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            boolean r = passengerStorage.removePassenger(id);
            if (!r) {
                return new Response("Could not remove passenger", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Passenger removed successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}