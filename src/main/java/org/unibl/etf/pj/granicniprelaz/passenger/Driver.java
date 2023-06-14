package org.unibl.etf.pj.granicniprelaz.passenger;

public class Driver extends Passenger {

    public Driver(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Vozač: " + super.toString();
    }
}
