/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author cisne
 */
public class Main {

    public static void main(String[] args) {
        // Lanzar la ventana principal en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new airport.views.AirportFrame().setVisible(true);
            }
        });
    }

}
