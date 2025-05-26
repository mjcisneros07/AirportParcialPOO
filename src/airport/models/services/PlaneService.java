/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.services;

import airport.models.Plane;
import airport.models.storage.PlaneStorage;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cisne
 */
public class PlaneService {
    public static List<Plane> getAllPlanesSortedById() {
        return PlaneStorage.getInstance().getAll().stream().sorted((a, b) -> a.getId().compareTo(b.getId())).collect(Collectors.toList());
    }
}
