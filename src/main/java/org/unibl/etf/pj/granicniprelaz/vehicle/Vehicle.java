package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.controllers.SimulationController;
import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public abstract class Vehicle extends Thread implements Serializable {
    protected final String vehicleName;
    protected Driver driver;
    protected List<Passenger> passengers;


    protected PositionEnum positionAtBorder;
    protected boolean processedAtPolice;
    protected boolean processedAtCustoms;
    protected boolean hasIncident;
    protected int indexOfPositionAtColumn;
    protected int indexOfTerminalPosition = -1;
    protected int indexOfCustomsPosition = -1;


    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.positionAtBorder = PositionEnum.AT_COLUMN;
        this.processedAtCustoms = false;
        this.processedAtPolice = false;
        this.hasIncident = false;
    }

    @Override
    public void run() {
        while (SimulationController.simulationStarted && !SimulationController.simulationFinished && !this.positionAtBorder.equals(PositionEnum.FINISHED) && !this.positionAtBorder.equals(PositionEnum.SUSPENDED)) {
            synchronized (Simulation.pathWithTerminals) {
                if (SimulationController.simulationPaused) {
                    try {
                        Simulation.pathWithTerminals.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                move();

            }
            if (indexOfTerminalPosition != -1 && !this.processedAtPolice) {
                Simulation.pathWithTerminals.getPathFields().get(indexOfTerminalPosition).getTerminal().processVehicle(this);
            } else if (indexOfCustomsPosition != -1 && !this.processedAtCustoms)
                Simulation.pathWithTerminals.getPathFields().get(indexOfCustomsPosition).getTerminal().processVehicle(this);

            if (this.positionAtBorder.equals(PositionEnum.SUSPENDED)) {
                Simulation.numberOfVehiclesFinished++;
                if (indexOfCustomsPosition == -1) {
                    Simulation.pathWithTerminals.getPathFields().get(indexOfTerminalPosition).setVehicle(null);
                    Simulation.pathWithTerminals.getPathFields().get(indexOfTerminalPosition).getTerminal().setBusy(false);
                } else {
                    Simulation.pathWithTerminals.getPathFields().get(indexOfCustomsPosition).setVehicle(null);
                    Simulation.pathWithTerminals.getPathFields().get(indexOfCustomsPosition).getTerminal().setBusy(false);
                }
            }
        }
    }

    public void move() {

        if (positionAtBorder.equals(PositionEnum.AT_COLUMN)) {
            int indexAtMap = indexOfPositionAtColumn + Constants.START_INDEX;

            if (indexOfPositionAtColumn > 0 && !Simulation.pathWithTerminals.getPathFields().get(indexAtMap - 1).isHasVehicle()) {
                Simulation.pathWithTerminals.getPathFields().get(indexAtMap).setVehicle(null);
                indexOfPositionAtColumn -= 1;
                Simulation.pathWithTerminals.getPathFields().get(indexAtMap - 1).setVehicle(this);

            } else if (indexOfPositionAtColumn == 0 && (indexOfTerminalPosition = checkPoliceTerminal()) != -1) {
                indexOfPositionAtColumn -= 1;
                Simulation.pathWithTerminals.getPathFields().get(Constants.START_INDEX).setVehicle(null);
                switch (indexOfTerminalPosition) {
                    case Constants.POLICE_TERMINAL_1 -> setVehicleAtTerminal(Constants.POLICE_TERMINAL_1);
                    case Constants.POLICE_TERMINAL_2 -> setVehicleAtTerminal(Constants.POLICE_TERMINAL_2);
                    case Constants.POLICE_TERMINAL_FOR_TRUCKS ->
                            setVehicleAtTerminal(Constants.POLICE_TERMINAL_FOR_TRUCKS);
                }
            }
        } else if (positionAtBorder.equals(PositionEnum.AT_POLICE_TERMINAL) && ((indexOfCustomsPosition = checkCustomsTerminal()) != -1) && processedAtPolice) {
            Simulation.pathWithTerminals.getPathFields().get(indexOfTerminalPosition).setVehicle(null);
            Simulation.pathWithTerminals.getPathFields().get(indexOfTerminalPosition).getTerminal().setBusy(false);
            switch (indexOfCustomsPosition) {
                case Constants.CUSTOMS_TERMINAL_1 -> setVehicleAtTerminal(Constants.CUSTOMS_TERMINAL_1);
                case Constants.CUSTOMS_TERMINAL_FOR_TRUCKS ->
                        setVehicleAtTerminal(Constants.CUSTOMS_TERMINAL_FOR_TRUCKS);
            }
        } else {
            if (positionAtBorder.equals(PositionEnum.AT_CUSTOMS_TERMINAL) && processedAtCustoms) {
                Simulation.pathWithTerminals.getPathFields().get(indexOfCustomsPosition).setVehicle(null);
                Simulation.pathWithTerminals.getPathFields().get(indexOfCustomsPosition).getTerminal().setBusy(false);

                positionAtBorder = PositionEnum.FINISHED;
                ++Simulation.numberOfVehiclesFinished;
            }
        }
    }


    private void setVehicleAtTerminal(int indexOfTerminal) {
        if (indexOfTerminal < Constants.POLICE_TERMINAL_1) {
            positionAtBorder = PositionEnum.AT_CUSTOMS_TERMINAL;
        } else {
            positionAtBorder = PositionEnum.AT_POLICE_TERMINAL;
        }
        Simulation.pathWithTerminals.getPathFields().get(indexOfTerminal).setVehicle(this);
        Simulation.pathWithTerminals.getPathFields().get(indexOfTerminal).getTerminal().setBusy(true);
    }

    public int checkPoliceTerminal() {
        if (!Simulation.pathWithTerminals.getPathFields().get(Constants.POLICE_TERMINAL_1).isHasVehicle() && !Simulation.pathWithTerminals.getPathFields().get(Constants.POLICE_TERMINAL_1).getTerminal().isBlocked()) {
            return Constants.POLICE_TERMINAL_1;
        } else if (!Simulation.pathWithTerminals.getPathFields().get(Constants.POLICE_TERMINAL_2).isHasVehicle() && !Simulation.pathWithTerminals.getPathFields().get(Constants.POLICE_TERMINAL_2).getTerminal().isBlocked()) {
            return Constants.POLICE_TERMINAL_2;
        } else {
            return -1;
        }
    }

    public int checkCustomsTerminal() {
        if (!Simulation.pathWithTerminals.getPathFields().get(Constants.CUSTOMS_TERMINAL_1).isHasVehicle() && !Simulation.pathWithTerminals.getPathFields().get(Constants.CUSTOMS_TERMINAL_1).getTerminal().isBlocked()) {
            return Constants.CUSTOMS_TERMINAL_1;
        } else {
            return -1;
        }
    }


    public String getVehicleName() {
        return vehicleName;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public PositionEnum getPositionAtBorder() {
        return positionAtBorder;
    }

    public void setPosition(PositionEnum positionAtBorder) {
        this.positionAtBorder = positionAtBorder;
    }

    public int getIndexOfPositionAtColumn() {
        return indexOfPositionAtColumn;
    }

    public void setIndexOfPositionAtColumn(int positionAtColumn) {
        this.indexOfPositionAtColumn = positionAtColumn;
    }

    public void setPositionAtBorder(PositionEnum positionAtBorder) {
        this.positionAtBorder = positionAtBorder;
    }

    public boolean isProcessedAtPolice() {
        return processedAtPolice;
    }

    public void setProcessedAtPolice(boolean processedAtPolice) {
        this.processedAtPolice = processedAtPolice;
    }

    public boolean isProcessedAtCustoms() {
        return processedAtCustoms;
    }

    public void setProcessedAtCustoms(boolean processedAtCustoms) {
        this.processedAtCustoms = processedAtCustoms;
    }

    public boolean isHasIncident() {
        return hasIncident;
    }

    public void setHasIncident(boolean hasIncident) {
        this.hasIncident = hasIncident;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleName, vehicle.vehicleName) && Objects.equals(driver, vehicle.driver) && Objects.equals(passengers, vehicle.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleName, driver, passengers);
    }

    @Override
    public String toString() {
        StringBuilder vehicleInfo = new StringBuilder("Status: " + positionAtBorder.toString() + ".\n" + driver).append("\nLista putnika: ");
        for (Passenger p : passengers) {
            vehicleInfo.append("\n").append(p);
        }
        return vehicleInfo.toString();
    }
}


