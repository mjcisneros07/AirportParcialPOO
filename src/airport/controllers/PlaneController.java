/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;
import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.models.Plane;
import airport.models.storage.PlaneStorage;
/**
 *
 * @author cisne
 */
public class PlaneController {
    public static Response createPlane(String id, String brand, String model, int maxCapacity, String airline) {
        try {
            // Validaciones básicas
            if (id == null || id.isEmpty()) {
                return new Response("Plane ID cannot be empty", Status.BAD_REQUEST);
            }
            if (brand == null || brand.isEmpty()) {
                return new Response("Brand cannot be empty", Status.BAD_REQUEST);
            }
            if (model == null || model.isEmpty()) {
                return new Response("Model cannot be empty", Status.BAD_REQUEST);
            }
            if (airline == null || airline.isEmpty()) {
                return new Response("Airline cannot be empty", Status.BAD_REQUEST);
            }
            if (maxCapacity <= 0) {
                return new Response("Max capacity must be positive", Status.BAD_REQUEST);
            }

            PlaneStorage planeStorage = PlaneStorage.getInstance();
            if (planeStorage.exists(id)) {
                return new Response("Plane already exists", Status.CONFLICT);
            }

            Plane plane = new Plane(id, brand, model, maxCapacity, airline);
            boolean añadido = planeStorage.addPlane(plane);
            if (!añadido) {
                return new Response("Could not add plane", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Plane created successfully", Status.CREATED, plane);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPlane(String id) {
        try {
            PlaneStorage planeStorage = PlaneStorage.getInstance();
            Plane p = planeStorage.getPlane(id);
            if (p == null) {
                return new Response("Plane not found", Status.NOT_FOUND);
            }
            return new Response("Plane found", Status.OK, p);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response removePlane(String id) {
        try {
            PlaneStorage planeStorage = PlaneStorage.getInstance();
            if (!planeStorage.exists(id)) {
                return new Response("Plane not found", Status.NOT_FOUND);
            }
            boolean r = planeStorage.removePlane(id);
            if (!r) {
                return new Response("Could not remove plane", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Plane removed successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
