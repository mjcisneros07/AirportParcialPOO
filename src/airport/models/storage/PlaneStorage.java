/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author cisne
 */
public class PlaneStorage {
    private ArrayList<Plane> planes;
    
    private static PlaneStorage instance;
    
    private PlaneStorage(){
        this.planes = new ArrayList<>();
    }
    
    public static PlaneStorage getInstance(){
        if (instance == null){
            instance = new PlaneStorage();
        }
        return instance;
    }
    
    public boolean addPlane(Plane plane){
        for (Plane p: this.planes){
            if (p.getId().equals(plane.getId())){
                return false;
            }
        }
        this.planes.add(plane);
        return true;
    }
    
    public boolean removePlane(String id){
        for (Plane p: this.planes){
            if (p.getId().equals(id)){
                this.planes.remove(p);
                return true;
            }
        }
        return false;
    }

    public Plane getPlane(String id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean exists(String id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
