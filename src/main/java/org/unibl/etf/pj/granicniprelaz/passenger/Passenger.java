package org.unibl.etf.pj.granicniprelaz.passenger;

import java.io.Serializable;


public class Passenger extends Person implements Serializable {
    public Passenger(String name) {
        super(name);

    }

    @Override
    public String toString() {
        return "Putnik: " + super.toString();
    }
}
