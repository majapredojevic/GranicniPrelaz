package org.unibl.etf.pj.granicniprelaz.simulation;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.terminal.CustomsTerminal;
import org.unibl.etf.pj.granicniprelaz.terminal.PoliceTerminal;
import org.unibl.etf.pj.granicniprelaz.terminal.Terminal;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.Car;
import org.unibl.etf.pj.granicniprelaz.vehicle.Truck;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Simulation extends Thread{

    private List<Terminal> policeTerminals;
    private List<Terminal> customsTerminals;

    private List<Vehicle> vehicles;
    public static Queue<Vehicle> queueOfVehicles;


    public Simulation() {

        policeTerminals = new ArrayList<>();
        customsTerminals = new ArrayList<>();
        vehicles = new ArrayList<>();
        queueOfVehicles = new LinkedList<>();;

//        //add Terminals
//        for (int i = 0; i < Constants.NUMBER_OF_POLICE_TERMINALS; i++) {
//            policeTerminals.add(new PoliceTerminal());
//        }
//        for (int i = 0; i < Constants.NUMBER_OF_CUSTOMS_TERMINALS; i++) {
//            customsTerminals.add(new CustomsTerminal());
//        }

        //add vehicles
        for (int i = 0; i < Constants.NUMBER_OF_CARS; i++) {
            vehicles.add(new Car());
        }
        for (int i = 0; i < Constants.NUMBER_OF_BUSSES; i++) {
            vehicles.add(new Bus());
        }
        for (int i = 0; i < Constants.NUMBER_OF_TRUCKS; i++) {
            vehicles.add(new Truck());
        }
        Collections.shuffle(vehicles);
        for(Vehicle v: vehicles) {
            v.setPositionAtQueue(List.of(vehicles).indexOf(v));
        }
        queueOfVehicles.addAll(vehicles);
    }

    @Override
    public void run() {

    }

}
