/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Flight;
import airport.models.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cisne
 */
public class LocationStorage {

    private ArrayList<Location> locations;

    private static LocationStorage instance;

    private LocationStorage() {
        this.locations = new ArrayList<>();
    }

    public static LocationStorage getInstance() {
        if (instance == null) {
            instance = new LocationStorage();
        }
        return instance;
    }

    public boolean addLocation(Location location) {
        for (Location l : this.locations) {
            if (l.getAirportId() == location.getAirportId()) {
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }

    public Location getLocation(String airportId) {
        for (Location l : this.locations) {
            if (l.getAirportId() == airportId) {
                return l;
            }
        }
        return null;
    }

    public boolean removeLocation(String airportId) {
        for (Location l : this.locations) {
            if (l.getAirportId() == airportId) {
                this.locations.remove(l);
                return true;
            }
        }
        return false;
    }

    public boolean exists(String airportId) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(airportId)) {
                return true;
            }
        }
        return false;
    }
    public List<Location> getAll(){
        return new ArrayList<>(this.locations);
    }
}
