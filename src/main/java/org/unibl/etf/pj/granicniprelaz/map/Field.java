package org.unibl.etf.pj.granicniprelaz.map;

import org.unibl.etf.pj.granicniprelaz.terminal.Terminal;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.util.Objects;

public class Field {

        private final int xPosition;
        private final int yPosition;

    private boolean hasTerminal;
    private Terminal terminal;

        private boolean hasVehicle;
        private Vehicle vehicle;




        public Field(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;

            this.hasTerminal = false;
            this.hasVehicle = false;
        }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public boolean isHasTerminal() {
        return hasTerminal;
    }

    public void setHasTerminal(boolean hasTerminal) {
        this.hasTerminal = hasTerminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public boolean isHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
