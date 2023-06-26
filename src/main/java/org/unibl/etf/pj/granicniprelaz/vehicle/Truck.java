package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.gadgets.CustomsDocumentation;
import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Truck extends Vehicle {
    private static int ID = 1;
    private final double declaredWeight;
    private final double realWeight;
    private CustomsDocumentation document;

    public Truck() {
        super("K" + ID);
        this.driver = new Driver("Vozač K" + ID, super.getVehicleName());
        this.passengers = new ArrayList<>();
        Random rand = new Random();
        declaredWeight = 3.0 + rand.nextDouble(3.0);

        //real weight calculation
        if (rand.nextInt(100) < 20) {
            realWeight = declaredWeight + declaredWeight * (1 + rand.nextDouble(29.0)) / 100;
        } else {
            realWeight = declaredWeight;
        }
        //generate customs documentation
        if (rand.nextInt(100) < 50) {
            this.document = new CustomsDocumentation(this.getVehicleName(), this.realWeight);
        }
        //add passengers
        int numberOfPassengers = rand.nextInt(Constants.MAX_NUM_OF_PASSENGERS_IN_TRUCK);
        for (int i = 0; i < numberOfPassengers; i++) {
            //add passenger
            Passenger passenger = new Passenger("Putnik" + (i + 1) + " K" + ID,this.getVehicleName());
            this.passengers.add(passenger);
        }
        ID++;
    }

    public static int getID() {
        return ID;
    }

    public double getDeclaredWeight() {
        return declaredWeight;
    }

    public double getRealWeight() {
        return realWeight;
    }

    public CustomsDocumentation getDocument() {
        return document;
    }

    public void setDocument(CustomsDocumentation document) {
        this.document = document;
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
    public int checkPoliceTerminal() {
        if (!Simulation.pathWithTerminals.getPathFields().get(Constants.POLICE_TERMINAL_FOR_TRUCKS).isHasVehicle() && !Simulation.pathWithTerminals.getPathFields().get(4).getTerminal().isBlocked()) {
            return Constants.POLICE_TERMINAL_FOR_TRUCKS;
        } else {
            return -1;
        }
    }

    @Override
    public int checkCustomsTerminal() {
        if (!Simulation.pathWithTerminals.getPathFields().get(Constants.CUSTOMS_TERMINAL_FOR_TRUCKS).isHasVehicle() && !Simulation.pathWithTerminals.getPathFields().get(4).getTerminal().isBlocked()) {
            return Constants.CUSTOMS_TERMINAL_FOR_TRUCKS;
        } else {
            return -1;
        }
    }
    @Override
    public String toString() {
        StringBuilder truckInfo = new StringBuilder("[Kamion]").append("\n").append("Ime: ").append(getVehicleName())
                .append("\n").append("Kamion")
                .append(getDocument() != null ? " ima " : " nema ").append("carinsku dokumentaciju.")
                .append("\n").append("Deklarisana težina = ")
                .append(String.format("%.2ft\n",declaredWeight)).append("Izmjerena težina = ")
                .append(String.format("%.2ft\n",realWeight)).append(super.toString());
        return truckInfo.toString();
    }
}
