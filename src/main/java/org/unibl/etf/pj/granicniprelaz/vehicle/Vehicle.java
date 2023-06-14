package org.unibl.etf.pj.granicniprelaz.vehicle;

import org.unibl.etf.pj.granicniprelaz.passenger.Driver;
import org.unibl.etf.pj.granicniprelaz.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Vehicle {
    protected final String name;
    protected Driver driver;
    protected List<Passenger> passengers;

    protected PositionEnum position;
    protected int positionAtQueue;
    protected  boolean suspended;


    public Vehicle(String name) {
        this.name = name;
        this.position = PositionEnum.IN_QUEUE;
        this.suspended = false;
    }

    public void move() {
        if (position.equals(PositionEnum.IN_QUEUE) && positionAtQueue > 0) {
            positionAtQueue -= 1;
        } else {
            positionAtQueue = -1;

            if (positionAtQueue == 0) {
                position = PositionEnum.AT_POLICE_TERMINAL;
            } else if (position.equals(PositionEnum.AT_POLICE_TERMINAL) && !suspended) {
                position = PositionEnum.AT_CUSTOMS_TERMINAL;
            } else {
                position = PositionEnum.FINISHED;

            }
        }
    }

    public String getName() {
        return name;
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

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public int getPositionAtQueue() {
        return positionAtQueue;
    }

    public void setPositionAtQueue(int positionAtQueue) {
        this.positionAtQueue = positionAtQueue;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(name, vehicle.name) && Objects.equals(driver, vehicle.driver) && Objects.equals(passengers, vehicle.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, driver, passengers);
    }

    @Override
    public String toString() {
        StringBuilder vehicleInfo = new StringBuilder( name + " je " + position.toString() + ".\nVozač: " + driver + "Putnici:\n");
        for (Passenger p : passengers) {
            vehicleInfo.append(p);
        }
        return vehicleInfo.toString();
    }
}
