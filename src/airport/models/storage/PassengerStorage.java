/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.models.storage;

import airport.models.Passenger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
    
    public boolean exists(long id) {
        for (Passenger p : this.passengers) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public List<Passenger> getAll(){
        return new ArrayList<>(this.passengers);
    }
    
    public void saveAll(List<Passenger> passengers) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("passengers.txt", false))) {
        for (Passenger p : passengers) {
            writer.println(p.getId() + "," +
                           p.getFirstname() + "," +
                           p.getLastname() + "," +
                           p.getBirthDate() + "," +
                           p.getCountryPhoneCode() + "," +
                           p.getPhone() + "," +
                           p.getCountry());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
