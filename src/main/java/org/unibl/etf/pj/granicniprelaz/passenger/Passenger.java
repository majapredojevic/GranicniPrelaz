package org.unibl.etf.pj.granicniprelaz.passenger;
public class Passenger extends Person {
    private final String vehicleName;

    public Passenger(String name, String vehicleName) {
        super(name);
        this.vehicleName = vehicleName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    @Override
    public String toString() {
        return "[Putnik] " + super.toString();
    }
}
