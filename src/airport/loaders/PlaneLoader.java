/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.loaders;
import airport.models.Plane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cisne
 */
public class PlaneLoader {

    public static ArrayList<Plane> loadPlanes(String filename) {
        ArrayList<Plane> planes = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        try ( BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
            }
            JSONArray arr = new JSONArray(contentBuilder.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Plane plane = new Plane(
                        obj.getString("id"),
                        obj.getString("brand"),
                        obj.getString("model"),
                        obj.getInt("maxCapacity"),
                        obj.getString("airline")
                );
                planes.add(plane);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo de aviones: " + e.getMessage());
        }
        return planes;
    }

}
