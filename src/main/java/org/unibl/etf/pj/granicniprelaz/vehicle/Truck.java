package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.gadgets.CustomsDocument;
import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Truck extends Vehicle {
    private static int ID = 1;

    private double declaredWeight;
    private double realWeight;
    private CustomsDocument document;

    public Truck() {
        super("K"+ ID);
        this.driver = new Driver("Vozač K" + ID);
        this.passengers = new ArrayList<>();
        Random rand = new Random();

        declaredWeight = 3.0 + rand.nextDouble(3.0);

        //real weight calculation
        if(rand.nextInt(100) < 20) {
            realWeight = declaredWeight + declaredWeight * (1+rand.nextDouble(29.0))/100;
        }
        else {
            realWeight = declaredWeight;
        }

        //add passengers
        int numberOfPassengers = rand.nextInt(Constants.MAX_NUM_OF_PASSENGERS_IN_TRUCK);
        for(int i = 0; i< numberOfPassengers; i++) {
            //add passenger
            Passenger passenger = new Passenger("Putnik" + (i+1) + " K" + ID);
            this.passengers.add(passenger);
            }
        ID++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Truck)) return false;
        if (!super.equals(o)) return false;
        Truck truck = (Truck) o;
        return Double.compare(truck.declaredWeight, declaredWeight) == 0 && Double.compare(truck.realWeight, realWeight) == 0 && Objects.equals(document, truck.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), declaredWeight, realWeight, document);
    }

    @Override
    public String toString() {
        return "Kamion: " + "težina=" + declaredWeight +
                "t, stvarna težina=" + realWeight +
                "t\n" + super.toString();
    }
}
