/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Flight;
import airport.models.Location;
import airport.models.Passenger;
import airport.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author cisne
 */
public final class Storage {
    private ArrayList<Flight> flights;
    private ArrayList<Location> locations;
    private ArrayList<Passenger> passengers;
    private ArrayList<Plane> planes;
    
    private static Storage instance;
    
    private Storage (){
        this.flights = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.planes = new ArrayList<>();
    }
    
    public static Storage getInstance() {
        if (instance == null){
            instance = new Storage();
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
}
