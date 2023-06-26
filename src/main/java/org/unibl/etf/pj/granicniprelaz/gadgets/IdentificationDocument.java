package org.unibl.etf.pj.granicniprelaz.gadgets;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class IdentificationDocument implements Serializable {
    private static int IdentificationNumber = 1;
    private final int ID;
    private boolean valid;


    public IdentificationDocument() {
        this.ID = IdentificationNumber++;

        Random rand = new Random();
        int number = rand.nextInt(100);

        if (number < 3) {
            this.valid = false;
        } else {
            this.valid = true;
        }
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentificationDocument)) return false;
        IdentificationDocument that = (IdentificationDocument) o;
        return ID == that.ID && valid == that.valid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, valid);
    }

    @Override
    public String toString() {
        return "Dokument: " + this.ID + " je " + (isValid() ? "validan" : "neispravan");
    }

}
