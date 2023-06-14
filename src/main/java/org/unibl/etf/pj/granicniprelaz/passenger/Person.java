package org.unibl.etf.pj.granicniprelaz.passenger;

import org.unibl.etf.pj.granicniprelaz.gadgets.IdentificationDocument;

import java.util.Objects;

public abstract class Person {
    private static int identificator = 1;
    private final int ID;

    private String name;
    private IdentificationDocument identificationDocument;

    public Person(String name) {
        this.ID = identificator++;
        this.name = name;
        this.identificationDocument = new IdentificationDocument();
    }

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdentificationDocument getIdentificationDocument() {
        return identificationDocument;
    }

    public void setIdentificationDocument(IdentificationDocument identificationDocument) {
        this.identificationDocument = identificationDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return ID == person.ID && Objects.equals(name, person.name) && Objects.equals(identificationDocument, person.identificationDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, identificationDocument);
    }

    @Override
    public String toString() {
        return "ID: " + ID + ", ime: " + name + ", " + identificationDocument;
    }
}
