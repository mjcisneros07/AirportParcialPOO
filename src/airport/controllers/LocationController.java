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

    public static Response createLocation(String airportId, String airportName, String airportCity, String airportCountry, Double airportLatitude, Double airportLongitude) {
        try {
            //Validación del formato de airporId
            if (airportId == null || !airportId.matches("^[A-Z]{3}$")) {
                return new Response("Airport ID must follow the format XXX", Status.BAD_REQUEST);
            }

            //Se valida si el airportID existe o no
            LocationStorage locationStorage = LocationStorage.getInstance();
            if (locationStorage.exists(airportId)) {
                return new Response("Airport ID already exists", Status.BAD_REQUEST);
            }

            //Validación del resto de informamción sobre el aeropuerto
            if (airportName == null) {
                return new Response("Airport Name cannot be null", Status.BAD_REQUEST);
            }
            if (airportCity == null) {
                return new Response("Airport City cannot be null", Status.BAD_REQUEST);
            }
            if (airportCountry == null) {
                return new Response("Airport Country cannot be null", Status.BAD_REQUEST);
            }

            //Validación de longitud y latitud
            // Primero valida si son nulos
            if (airportLatitude == null) {
                return new Response("The latitude cannot be null", Status.BAD_REQUEST);
            }
            if (airportLongitude == null) {
                return new Response("The longitude cannot be null", Status.BAD_REQUEST);
            }
            if (airportLatitude < -90 || airportLatitude > 90) {
                return new Response("The latitude must be between -90 and 90.", Status.BAD_REQUEST);
            }
            if (airportLatitude == null) {
                return new Response("The latitude cannot be null", Status.BAD_REQUEST);
            }
            if (airportLongitude < -180 || airportLongitude > 180) {
                return new Response("The longitude must be between -180 and 180.", Status.BAD_REQUEST);
            }
            if (airportLongitude == null) {
                return new Response("The latitude cannot be null", Status.BAD_REQUEST);
            }
            
            Location location = new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude);
            boolean añadido = locationStorage.addLocation(location);
            if(!añadido){
                return new Response ("Could not add Location", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response ("Location created succesfully", Status.CREATED);

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
