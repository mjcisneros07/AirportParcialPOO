/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Flight;
import airport.models.Location;
import airport.models.Passenger;
import airport.models.Plane;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author cisne
 */
public class FlightStorage {
    private ArrayList<Flight> flights;
    
    private static FlightStorage instance;
    
    private FlightStorage (){
        this.flights = new ArrayList<>();
    }
    
    public static FlightStorage getInstance() {
        if (instance == null){
            instance = new FlightStorage();
        }
        return instance;
    }
    
    public boolean addFlight (Flight flight){
        for (Flight f: this.flights){
            if (f.getId() == flight.getId()){
                return false;
            }
        }
        this.flights.add(flight);
        return true;
    }
    public Flight getFlight (String id){
        for (Flight f: this.flights){
            if (f.getId() == id){
                return f;
            }
        }
        return null;
    }
    
    public boolean removeFlight(String id){
        for (Flight f: this.flights){
            if (f.getId() == id){
                this.flights.remove(f);
                return true;
            }
        }
        return false;
    }
}
