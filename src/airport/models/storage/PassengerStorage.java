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
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    public void saveAll(List<Passenger> passengers, String filename) {
        JSONArray flightArray = new JSONArray();

        for (Passenger passenger : passengers) {
            JSONObject obj = new JSONObject();
            obj.put("id", passenger.getId());
            obj.put("firstName", passenger.getFirstname());
            obj.put("lastName", passenger.getLastname());
            obj.put("birthDate", passenger.getBirthDate());
            obj.put("countryPhoneCode", passenger.getCountryPhoneCode());
            obj.put("phone", passenger.getPhone());
            obj.put("country", passenger.getCountry());


            flightArray.put(obj);
        }

        try ( FileWriter file = new FileWriter(filename)) {
            file.write(flightArray.toString(4)); // 4 es la indentación del JSON
        } catch (IOException e) {
            System.err.println("Error guardando pasajeros: " + e.getMessage());
        }
    }
}
