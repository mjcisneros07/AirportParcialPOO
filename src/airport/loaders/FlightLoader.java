/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.loaders;

import airport.models.Flight;
import airport.models.Location;
import airport.models.Plane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cisne
 */
public class FlightLoader {

    public static ArrayList<Flight> loadFlights(String filename, ArrayList<Plane> planes, ArrayList<Location> locations) {
        ArrayList<Flight> flights = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        try ( BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
            }
            JSONArray arr = new JSONArray(contentBuilder.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                // Buscar avión por ID
                Plane plane = null;
                String planeId = obj.getString("plane");
                for (Plane p : planes) {
                    if (p.getId().equals(planeId)) {
                        plane = p;
                        break;
                    }
                }

                // Buscar ubicaciones por ID
                Location dep = null, arrv = null, scale = null;
                String depId = obj.getString("departureLocation");
                String arrvId = obj.getString("arrivalLocation");
                String scaleId = obj.isNull("scaleLocation") ? null : obj.getString("scaleLocation");

                for (Location loc : locations) {
                    if (loc.getAirportId().equals(depId)) {
                        dep = loc;
                    }
                    if (loc.getAirportId().equals(arrvId)) {
                        arrv = loc;
                    }
                    if (scaleId != null && loc.getAirportId().equals(scaleId)) {
                        scale = loc;
                    }
                }

                LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));
                int hArr = obj.getInt("hoursDurationArrival");
                int mArr = obj.getInt("minutesDurationArrival");
                int hScale = obj.optInt("hoursDurationScale", 0);
                int mScale = obj.optInt("minutesDurationScale", 0);

                Flight flight;
                if (scale == null) {
                    flight = new Flight(obj.getString("id"), plane, dep, arrv, departureDate, hArr, mArr);
                } else {
                    flight = new Flight(obj.getString("id"), plane, dep, scale, arrv, departureDate, hArr, mArr, hScale, mScale);
                }
                flights.add(flight);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo de vuelos: " + e.getMessage());
        }
        return flights;
    }
}
