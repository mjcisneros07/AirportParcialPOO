/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Flight;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cisne
 */
public class FlightStorage {

    private ArrayList<Flight> flights;

    private static FlightStorage instance;

    private FlightStorage() {
        this.flights = new ArrayList<>();
    }

    public static FlightStorage getInstance() {
        if (instance == null) {
            instance = new FlightStorage();
        }
        return instance;
    }

    public boolean addFlight(Flight flight) {
        for (Flight f : this.flights) {
            if (f.getId() == flight.getId()) {
                return false;
            }
        }
        this.flights.add(flight);
        return true;
    }

    public Flight getFlight(String id) {
        for (Flight f : this.flights) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public boolean removeFlight(String id) {
        for (Flight f : this.flights) {
            if (f.getId() == id) {
                this.flights.remove(f);
                return true;
            }
        }
        return false;
    }

    public boolean exists(String id) {
        for (Flight f : this.flights) {
            if (f.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Flight> getAll() {
        return new ArrayList<>(this.flights);
    }

    public static void saveAll(List<Flight> flights, String filename) {
        JSONArray flightArray = new JSONArray();

        for (Flight flight : flights) {
            JSONObject obj = new JSONObject();
            obj.put("id", flight.getId());
            obj.put("plane", flight.getPlane().getId());
            obj.put("departureLocation", flight.getDepartureLocation().getAirportId());
            obj.put("arrivalLocation", flight.getArrivalLocation().getAirportId());
            obj.put("departureDate", flight.getDepartureDate().toString());

            if (flight.getScaleLocation() != null) {
                obj.put("scaleLocation", flight.getScaleLocation().getAirportId());
                obj.put("hoursDurationScale", flight.getHoursDurationScale());
                obj.put("minutesDurationScale", flight.getMinutesDurationScale());
            } else {
                obj.put("scaleLocation", JSONObject.NULL);
                obj.put("hoursDurationScale", 0);
                obj.put("minutesDurationScale", 0);
            }

            obj.put("hoursDurationArrival", flight.getHoursDurationArrival());
            obj.put("minutesDurationArrival", flight.getMinutesDurationArrival());

            flightArray.put(obj);
        }

        try ( FileWriter file = new FileWriter(filename)) {
            file.write(flightArray.toString(4)); // 4 es la indentación del JSON
        } catch (IOException e) {
            System.err.println("Error guardando vuelos: " + e.getMessage());
        }
    }
}
