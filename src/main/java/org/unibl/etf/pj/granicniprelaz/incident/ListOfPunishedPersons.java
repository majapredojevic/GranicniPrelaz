package org.unibl.etf.pj.granicniprelaz.incident;

import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfPunishedPersons implements Serializable {

    private List<Passenger> persons ;
    public ListOfPunishedPersons() {
        this.persons = new ArrayList<>();
    }

    public List<Passenger> getPersons() {
        return persons;
    }

    public void setPersons(List<Passenger> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        for(Passenger p: persons) {
            list.append(p.toString()).append(", Ime vozila: ").append(p.getVehicleName()).append("\n");
        }
        return list.toString();
    }
}
