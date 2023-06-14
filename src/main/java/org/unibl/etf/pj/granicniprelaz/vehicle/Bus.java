package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.gadgets.Suitcase;
import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bus extends Vehicle {
    private static int ID = 1;
    private List<Suitcase> luggage;

    public Bus() {
        super("A"+ ID);
        this.driver = new Driver("Vozač A" + ID);
        this.passengers = new ArrayList<>();
        this.luggage = new ArrayList<>();
        Random rand = new Random();
        int numberOfPassengers = rand.nextInt(Constants.MAX_NUM_OF_PASSENGERS_IN_BUS);
        for(int i = 0; i< numberOfPassengers; i++) {
            //add passenger
            Passenger passenger = new Passenger("Putnik" + (i+1) + " B" + ID);
            this.passengers.add(passenger);
            //add suitcase
            int hasSuitcase = rand.nextInt(100);
            if(hasSuitcase < 70) {
                luggage.add(new Suitcase(passenger.getID()));
            }
        }
        ID++;
    }


    @Override
    public String toString() {
        StringBuilder vehicleInfo = new StringBuilder( "Autobus: " + super.toString() +"\nKoferi:\n");
        for (Suitcase s : luggage) {
            vehicleInfo.append(s);
        }
        return vehicleInfo.toString() ;
    }
}
