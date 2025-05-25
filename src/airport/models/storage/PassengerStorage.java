/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Passenger;
import java.util.ArrayList;

/**
 *
 * @author cisne
 */
public class PassengerStorage {
    private ArrayList<Passenger> passengers;
    
    private static PassengerStorage instance;
    
    private PassengerStorage(){
        this.passengers = new ArrayList<>();
    }
    
    public static PassengerStorage getInstance(){
        if (instance == null){
            instance = new PassengerStorage();
        }
        return instance;
    }
    
    public boolean addPassenger(Passenger passenger){
        for (Passenger p: this.passengers){
            if (p.getId()== passenger.getId()){
                return false;
            }
        }
        this.passengers.add(passenger);
        return true;
    }
    
    public Passenger getPassenger(long id){
        for (Passenger p: this.passengers){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
    
    public boolean removePassenger(long id){
        for (Passenger p: this.passengers){
            if (p.getId() == id){
                this.passengers.remove(p);
                return true;
            }
        }
        return false;
    }
}
