package org.unibl.etf.pj.granicniprelaz.terminal;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.PositionEnum;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.util.logging.Level;
import java.util.stream.Collectors;

public class PoliceTerminal extends Terminal {
    public PoliceTerminal(String name){
        super(name);
    }

    @Override
    public void processVehicle(Vehicle vehicle) {
        //check driver document
        if (!vehicle.getDriver().getIdentificationDocument().isValid()) {
            Simulation.listOfPunishedPersons.getPersons().add(vehicle.getDriver());
            try {
                Thread.sleep(vehicle instanceof Bus ? Constants.PASSENGER_AT_BUS_PROCESSING_TIME : Constants.PASSENGER_PROCESSING_TIME);
            } catch (InterruptedException exception) {
                SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
            }
            for(Passenger passenger: vehicle.getPassengers()) {
                Simulation.listOfPunishedPersons.getPersons().add(passenger);
            }
            vehicle.setPosition((PositionEnum.SUSPENDED));
            vehicle.setHasIncident(true);
        }
        else {
            //check passengers documents

            for (Passenger p : vehicle.getPassengers()) {
                if (!p.getIdentificationDocument().isValid()) {
                    Simulation.listOfPunishedPersons.getPersons().add(p);
                    vehicle.setHasIncident(true);
                }
                vehicle.setPassengers(vehicle.getPassengers().stream().filter(x -> x.getIdentificationDocument().isValid()).collect(Collectors.toList()));

                try {
                    Thread.sleep(vehicle instanceof Bus ? Constants.PASSENGER_AT_BUS_PROCESSING_TIME : Constants.PASSENGER_PROCESSING_TIME);
                } catch (InterruptedException exception) {
                    SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
                }
            }
        }
        vehicle.setProcessedAtPolice(true);
    }


}

