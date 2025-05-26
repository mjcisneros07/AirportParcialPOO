/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import airport.controllers.AirportController;
import airport.controllers.FlightController;
import airport.controllers.LocationController;
import airport.controllers.PassengerController;
import airport.controllers.PlaneController;
import airport.controllers.utils.Response;
import airport.loaders.FlightLoader;
import airport.loaders.LocationLoader;
import airport.loaders.PassengerLoader;
import airport.loaders.PlaneLoader;
import airport.models.Flight;
import airport.models.Location;
import airport.models.Passenger;
import airport.models.Plane;
import airport.views.AirportFrame;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.ArrayList;
import javax.swing.UIManager;

/**
 *
 * @author cisne
 */
public class Main {

    public static void main(String[] args) {
        
        ArrayList<Location> loadedLocations = LocationLoader.loadLocations("src/data/locations.json");
        for (Location loc : loadedLocations) {
            Response response = LocationController.createLocation(loc.getAirportId(), loc.getAirportName(), loc.getAirportCity(), loc.getAirportCountry(), loc.getAirportLatitude(), loc.getAirportLongitude());
        }
        ArrayList<Plane> loadedPlanes = PlaneLoader.loadPlanes("src/data/planes.json");
        for (Plane pl : loadedPlanes) {
            Response response = PlaneController.createPlane(pl.getId(), pl.getBrand(), pl.getModel(), pl.getMaxCapacity(), pl.getAirline());
        }
        ArrayList<Passenger> loadedPassengers = PassengerLoader.loadPassengers("src/data/passengers.json");
        for (Passenger pa : loadedPassengers) {
            Response response = PassengerController.createPassenger(pa);
        }
        ArrayList<Flight> loadedFlights = FlightLoader.loadFlights("src/data/flights.json", loadedPlanes, loadedLocations);
        for (Flight f : loadedFlights) {
            Response response = FlightController.createFlight(f.getId(), f.getPlane(), f.getScaleLocation(), f.getDepartureLocation(), f.getArrivalLocation(), f.getDepartureDate(), f.getHoursDurationArrival(), f.getMinutesDurationArrival(), f.getHoursDurationScale(), f.getMinutesDurationScale());
        }

        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AirportFrame frame = new AirportFrame();
                AirportController controller = new AirportController(frame);
                frame.setVisible(true);
            }
        });
    }
}
