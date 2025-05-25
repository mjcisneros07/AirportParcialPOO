/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.models.Location;
import airport.models.storage.LocationStorage;

/**
 *
 * @author apont
 */
public class LocationController {

    public static Response createLocation(Location location) {
        try {
            if (location == null) {
                return new Response("Location cannot be null", Status.BAD_REQUEST);
            }
            LocationStorage locationStorage = LocationStorage.getInstance();
            if (locationStorage.exists(location.getAirportId())) {
                return new Response("Location already exists", Status.CONFLICT);
            }
            boolean añadido = locationStorage.addLocation(location);
            if (!añadido) {
                return new Response("Could not add location", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Location created successfully", Status.CREATED, location);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getLocation(String airportId) {
        try {
            LocationStorage locationStorage = LocationStorage.getInstance();
            Location L = locationStorage.getLocation(airportId);
            if (L == null) {
                return new Response("Location not found", Status.NOT_FOUND);
            }
            return new Response("Location found", Status.OK, L);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response removeLocation(String airportId) {
        try {
            LocationStorage locationStorage = LocationStorage.getInstance();
            if (!locationStorage.exists(airportId)) {
                return new Response("Location not found", Status.NOT_FOUND);
            }
            boolean removido = locationStorage.removeLocation(airportId);
            if (!removido) {
                return new Response("Could not remove location", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Location removed successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
