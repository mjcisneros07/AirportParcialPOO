/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.services;

import airport.models.Location;
import airport.models.storage.LocationStorage;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cisne
 */
public class LocationService {

    public static List<Location> getAllLocationsSortedById() {
        return LocationStorage.getInstance().getAll().stream().sorted((a, b) -> a.getAirportId().compareTo(b.getAirportId())).collect(Collectors.toList());
    }
}
