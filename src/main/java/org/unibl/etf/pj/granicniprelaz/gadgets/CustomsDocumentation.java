package org.unibl.etf.pj.granicniprelaz.gadgets;

public class CustomsDocumentation {
private final String document;
private final boolean correctDocumentation = true;
public CustomsDocumentation(String truckName,double weight) {
    this.document = "Kamion" + truckName + "prevozi robu čija težina iznosi " + weight + ". " +
            "Roba je osigurana.";
}

    public String getDocument() {
        return document;
    }

    public boolean isCorrectDocumentation() {
        return correctDocumentation;
    }

    @Override
 public String toString() {
    return document + "Dokumentacija je " + (correctDocumentation ? "ispravna." : "neispravna.");
 }

}
