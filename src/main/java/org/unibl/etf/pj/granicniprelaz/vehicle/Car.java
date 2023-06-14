package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;

import java.util.ArrayList;
import java.util.Random;

public class Car extends Vehicle {
    private static int ID = 1;

    public Car() {
        super("V"+ ID);
        this.driver = new Driver("Vozač V" + ID);
        this.passengers = new ArrayList<>();
        Random rand = new Random();
        int numberOfPassengers = rand.nextInt(Constants.MAX_NUM_OF_PASSENGERS_IN_CAR);
        for(int i = 0; i< numberOfPassengers; i++) {
            Passenger passenger = new Passenger("Putnik" + (i+1) + " V" + ID);
            this.passengers.add(passenger);
        }
        ID++;
        }


    @Override
    public String toString() {
        return "Automobil: " + super.toString();
    }
}

