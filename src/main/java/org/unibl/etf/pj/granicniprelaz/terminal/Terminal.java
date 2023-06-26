package org.unibl.etf.pj.granicniprelaz.terminal;

import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

public abstract class Terminal {

    protected String terminalName;
    protected boolean blocked;
    protected boolean busy;


    public Terminal(String name) {
        this.blocked = false;
        this.busy = false;
        this.terminalName = name;
    }

    public String getTerminalName() {
        return terminalName;
    }
    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    public boolean isBusy() { return busy; }
    public void setBusy(boolean busy) { this.busy = busy;}

    public abstract void processVehicle(Vehicle vehicle);

    @Override
    public String toString() {
        return this.getTerminalName() + ": " + (this.isBlocked() ? "BLOKIRAN" : (this.isBusy() ? "ZAUZET" : "SLOBODAN"));
    }

}
