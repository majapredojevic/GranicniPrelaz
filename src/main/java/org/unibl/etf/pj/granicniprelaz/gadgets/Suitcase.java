package org.unibl.etf.pj.granicniprelaz.gadgets;

import java.util.Objects;
import java.util.Random;

public class Suitcase {

    private final int passengerID ;
    private boolean allowedItems;

    public Suitcase(int passengerID) {
        this.passengerID = passengerID;
        Random rand = new Random();
        int number = rand.nextInt(100);

        if(number < 10) {
            this.allowedItems = false;
        } else  {
            this.allowedItems = true;
        }
    }

    public void setAllowedItems(boolean allowedItems) {
        this.allowedItems = allowedItems;
    }

    public boolean isAllowedItems() {
        return allowedItems;
    }

    public int getPassengerID() {
        return passengerID;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suitcase)) return false;
        Suitcase suitcase = (Suitcase) o;
        return allowedItems == suitcase.allowedItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowedItems);
    }

    @Override
    public  String toString() {
        return "Kofer putnika: " + passengerID  +  (isAllowedItems() ? " nema" : " ima") + " nedozvoljene stvari.";
    }
}
