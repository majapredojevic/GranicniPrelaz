package org.unibl.etf.pj.granicniprelaz.terminal;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.vehicle.PositionEnum;
import org.unibl.etf.pj.granicniprelaz.vehicle.Truck;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.util.logging.Level;

public class CustomsTerminalForTrucks extends CustomsTerminal {

    public CustomsTerminalForTrucks(String name) {
        super(name);
    }

    @Override
    public void processVehicle(Vehicle vehicle) {
        final Truck t = (Truck) vehicle;

        StringBuilder truckInfo = new StringBuilder("Kamion " + t.getVehicleName() + " nije prešao granicu");

        if ((t.getDocument() != null && !t.getDocument().isCorrectDocumentation())) {
            truckInfo.append(" zbog neispravne dokumenacije.").append("\n");
            Simulation.listOfVehiclesWithIncidentAtCustoms.add(truckInfo.toString());
            t.setPosition(PositionEnum.SUSPENDED);
        } else if (t.getRealWeight() > t.getDeclaredWeight()) {
            truckInfo.append(", jer je pretovaren.").append("\n");
            Simulation.listOfVehiclesWithIncidentAtCustoms.add(truckInfo.toString());
            t.setPosition(PositionEnum.SUSPENDED);
        }
        try {
            Thread.sleep(Constants.PASSENGER_PROCESSING_TIME);
        } catch (InterruptedException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
        vehicle.setProcessedAtCustoms(true);

    }
}

