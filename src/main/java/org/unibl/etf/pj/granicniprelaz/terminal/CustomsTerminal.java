package org.unibl.etf.pj.granicniprelaz.terminal;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.gadgets.Suitcase;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.Car;
import org.unibl.etf.pj.granicniprelaz.vehicle.PositionEnum;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CustomsTerminal extends Terminal {

    public CustomsTerminal(String name) {
        super(name);
    }

    @Override
    public void processVehicle(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            try {
                Thread.sleep(Constants.CAR_WAITING_TIME_AT_CUSTOMS);
            } catch (InterruptedException exception) {
                SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
            }
            vehicle.setProcessedAtCustoms(true);
        } else {
            List<Passenger> personsWithIllegalThings = new ArrayList<>();
            Bus bus = (Bus) vehicle;

            //check driver suitcase
            final Suitcase driverSuitcase = bus.getSuitcaseByPassengerID(bus.getDriver().getID());
            if (driverSuitcase != null && !driverSuitcase.isAllowedItems()) {
                StringBuilder busInfo = new StringBuilder("Autobus " + bus.getVehicleName() + "je suspendovan, jer vozač " + bus.getDriver().getName() + " ima nedozvoljene stvari u koferu.\n").append("Lista putnika:").append("\n");
                for (Passenger p : bus.getPassengers()) {
                    busInfo.append("Putnik ").append(p.getName()).append(" je izbačen iz autobusa ")
                            .append(bus.getVehicleName()).append(" zbog vozača.").append("\n");
                }
                Simulation.listOfVehiclesWithIncidentAtCustoms.add(busInfo.toString());
                bus.setPosition((PositionEnum.SUSPENDED));
            }
            if (!bus.getPositionAtBorder().equals(PositionEnum.SUSPENDED)) {
                //check suitcases of passengers
                for (Passenger p : bus.getPassengers()) {
                    final Suitcase s = bus.getSuitcaseByPassengerID(p.getID());
                    if (s != null && !s.isAllowedItems()) {
                        personsWithIllegalThings.add(p);
                    }

                    try {
                        Thread.sleep(Constants.PASSENGER_AT_BUS_PROCESSING_TIME);
                    } catch (InterruptedException exception) {
                        SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
                    }
                }
            }

            bus.setPassengers(bus.getPassengers().stream().filter(x -> (bus.getSuitcaseByPassengerID(x.getID()) != null) && bus.getSuitcaseByPassengerID(x.getID()).isAllowedItems()).collect(Collectors.toList()));
            bus.setLuggage(bus.getLuggage().stream().filter(Suitcase::isAllowedItems).collect(Collectors.toList()));

            if (!personsWithIllegalThings.isEmpty()) {
                vehicle.setHasIncident(true);
                StringBuilder passengersInfo = new StringBuilder("Autobus " + bus.getVehicleName() + "je prošao carinu, ali je imao incident s putnicima:").append("\n");
                for (Passenger p : personsWithIllegalThings) {
                    passengersInfo.append("Putnik ").append(p.getName()).append(" je izbačen iz autobusa ")
                            .append(bus.getVehicleName()).append(", jer je imao nedozvoljene stvari u koferu.").append("\n");
                }
                Simulation.listOfVehiclesWithIncidentAtCustoms.add(passengersInfo.toString());
            }
            vehicle.setProcessedAtCustoms(true);
        }
    }
}