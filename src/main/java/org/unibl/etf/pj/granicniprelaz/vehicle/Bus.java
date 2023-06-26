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
        super("B"+ ID);

        this.passengers = new ArrayList<>();
        this.luggage = new ArrayList<>();

        this.driver = new Driver("Vozač B" + ID, super.getVehicleName());
        addSuitcase(driver.getID());

        Random rand = new Random();
        int numberOfPassengers = rand.nextInt(Constants.MAX_NUM_OF_PASSENGERS_IN_BUS);
        for(int i = 0; i< numberOfPassengers; i++) {
            //add passenger
            Passenger passenger = new Passenger("Putnik" + (i+1) + " B" + ID,super.getVehicleName());
            this.passengers.add(passenger);
            //add suitcase
            addSuitcase(passenger.getID());
        }
        ID++;
    }

    private void addSuitcase(int passengerID) {
        Random rand = new Random();
        int hasSuitcase = rand.nextInt(100);
        if(hasSuitcase < 70) {
            this.luggage.add(new Suitcase(passengerID));
        }
    }

    public List<Suitcase> getLuggage() {
        return luggage;
    }

    public Suitcase getSuitcaseByPassengerID(int passengerID) {
        for(Suitcase s: luggage) {
            if(s.getPassengerID() == passengerID)
                return s;
        }
        return null;
    }

    public void setLuggage(List<Suitcase> luggage) {
        this.luggage = luggage;
    }


    @Override
    public String toString() {
        StringBuilder vehicleInfo = new StringBuilder("[Autobus]").append("\n").append(super.toString())
                .append("\n").append("Koferi: ");
        for (Suitcase s : luggage) {
            vehicleInfo.append("\n").append(s);
        }
        return vehicleInfo.toString() ;
    }
}
