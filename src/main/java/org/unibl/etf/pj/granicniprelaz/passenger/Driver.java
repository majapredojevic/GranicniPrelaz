package org.unibl.etf.pj.granicniprelaz.passenger;

public class Driver extends Passenger {

    public Driver(String name,String vehicleName) {
        super(name,vehicleName);
    }

    @Override
    public String toString() {
        return super.toString().replace("[Putnik] ","[Vozač] ");
    }
}
