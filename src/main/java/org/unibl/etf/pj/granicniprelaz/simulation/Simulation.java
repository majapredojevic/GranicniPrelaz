package org.unibl.etf.pj.granicniprelaz.simulation;

import javafx.application.Platform;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.controllers.SimulationController;
import org.unibl.etf.pj.granicniprelaz.controllers.StartApplication;
import org.unibl.etf.pj.granicniprelaz.exception.MapLoadingException;
import org.unibl.etf.pj.granicniprelaz.incident.IncidentUtil;
import org.unibl.etf.pj.granicniprelaz.incident.ListOfPunishedPersons;
import org.unibl.etf.pj.granicniprelaz.json.PathJsonParser;
import org.unibl.etf.pj.granicniprelaz.map.Field;
import org.unibl.etf.pj.granicniprelaz.map.PathOnMap;
import org.unibl.etf.pj.granicniprelaz.terminal.*;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.Car;
import org.unibl.etf.pj.granicniprelaz.vehicle.Truck;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;

public class Simulation extends Thread {

    private final List<Terminal> customsTerminals;
    private final List<Terminal> policeTerminals;
    public static List<Vehicle> columnOfVehicles;

    public static final PathOnMap pathWithTerminals = initPath(Constants.PATH_ON_MAP);
    public static Integer numberOfVehiclesFinished;

    public static ListOfPunishedPersons listOfPunishedPersons;
    public static List<String> listOfVehiclesWithIncidentAtCustoms;


    public Simulation() {

        numberOfVehiclesFinished = 0;
        listOfPunishedPersons = new ListOfPunishedPersons();
        listOfVehiclesWithIncidentAtCustoms = new ArrayList<>();

        policeTerminals = new ArrayList<>();
        customsTerminals = new ArrayList<>();
        columnOfVehicles = new ArrayList<>();

        //add Police terminals

        policeTerminals.add(new PoliceTerminal("P1"));
        policeTerminals.add(new PoliceTerminal("P2"));
        policeTerminals.add(new PoliceTerminalForTrucks("PK"));

        //add Customs terminals
        customsTerminals.add(new CustomsTerminal("C1"));
        customsTerminals.add(new CustomsTerminalForTrucks("CK"));

        //add vehicles
        for (int i = 0; i < Constants.NUMBER_OF_CARS; i++) {
            columnOfVehicles.add(new Car());
        }
        for (int i = 0; i < Constants.NUMBER_OF_BUSSES; i++) {
            columnOfVehicles.add(new Bus());
        }
        for (int i = 0; i < Constants.NUMBER_OF_TRUCKS; i++) {
            columnOfVehicles.add(new Truck());
        }
        Collections.shuffle(columnOfVehicles);
        for (Vehicle v : columnOfVehicles) {
            v.setIndexOfPositionAtColumn(columnOfVehicles.indexOf(v));
        }
        //set terminals and vehicles at map
        addTerminalsAndVehiclesOnMap();

    }

    @Override
    public void run() {

        for (Vehicle v : columnOfVehicles)
            v.start();
        resetTerminalsState();
        while (SimulationController.simulationStarted && !SimulationController.simulationFinished) {

            if (numberOfVehiclesFinished.equals(Constants.NUMBER_OF_VEHICLES)) {
                SimulationController.simulationFinished = true;
            } else {
                synchronized (pathWithTerminals) {
                    if (SimulationController.simulationPaused) {
                        try {
                            pathWithTerminals.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshMap();
                    refreshDescription();
                }
                try {
                    Thread.sleep(Constants.WAITING_TIME);
                } catch (InterruptedException exception) {
                    SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
                }
            }
        }
        finishGame();
    }

    private void finishGame() {

        SimulationController.simulationStarted = false;
        SimulationController.simulationPaused = false;
        SimulationController.simulationFinished = true;

        IncidentUtil.writeListOfPunishedPersonsIntoFile(listOfPunishedPersons);
        IncidentUtil.writeCustomsIncident();

        listOfVehiclesWithIncidentAtCustoms.clear();
        listOfPunishedPersons.getPersons().clear();

        resetTerminalsState();
        refreshMap();
        refreshDescription();

        SimulationController.timeCounter = null;

        for (Vehicle v : columnOfVehicles) {
            v = null;
        }
    }

    public void refreshDescription() {
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < Constants.START_INDEX; i++) {
            Field field;
            synchronized (pathWithTerminals) {
                field = pathWithTerminals.getPathFields().get(i);
                description.append(field.getTerminal().toString()).append("\n");
            }
            if (!field.getTerminal().isBusy()) {
                description.append("Nema vozila na terminalu.").append("\n").append("\n");
            } else if ((field.getTerminal() instanceof PoliceTerminal && !field.getVehicle().isProcessedAtPolice()) || (field.getTerminal() instanceof CustomsTerminal && !field.getVehicle().isProcessedAtCustoms())) {
                description.append("Obrada vozila ").append(field.getVehicle().getVehicleName()).append(".").append("\n").append("\n");
            } else if (field.getVehicle().isHasIncident()) {
                description.append(field.getVehicle().getVehicleName()).append(" je obrađeno s incidentom.").append("\n").append("\n");
            } else {
                description.append(field.getVehicle().getVehicleName()).append(" je bez incidenta prošlo kontrolu.").append("\n").append("\n");
            }
        }

        StartApplication.simulationController.setLabelTerminalDescription(description.toString());
    }

    public void refreshMap() {
        Platform.runLater(() -> {
                    StartApplication.simulationController.initPath();
                    if (SimulationController.columnOfVehiclesController != null)
                        SimulationController.columnOfVehiclesController.initPath();
                }
        );
    }

    private static PathOnMap initPath(String pathName) {
        PathOnMap pathOnMap = null;
        try {
            pathOnMap = PathJsonParser.getPathFromJson(pathName);
        } catch (MapLoadingException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return pathOnMap;
    }

    private void addTerminalsAndVehiclesOnMap() {
        int i = 0;
        for (Terminal t : customsTerminals) {
            pathWithTerminals.getPathFields().get(i).setTerminal(t);
            pathWithTerminals.getPathFields().get(i).setHasTerminal(true);
            i++;
        }
        for (Terminal t : policeTerminals) {
            pathWithTerminals.getPathFields().get(i).setTerminal(t);
            pathWithTerminals.getPathFields().get(i).setHasTerminal(true);
            i++;
        }
        for (Vehicle v : columnOfVehicles) {
            pathWithTerminals.getPathFields().get(i).setVehicle(v);
            i++;
        }
    }

    private void resetTerminalsState() {
        try {
            PrintWriter pw = new PrintWriter(new File(Constants.TERMINALS_DIRECTORY + File.separator + Constants.STATE_OF_TERMINALS_FILE));
            for (int i = 0; i < Constants.NUMBER_OF_TERMINALS; i++) {
                pw.println("0");
            }
            pw.close();
        } catch (FileNotFoundException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
    }

}
