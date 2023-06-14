module org.unibl.etf.pj.granicniprelaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires json.simple;


    exports org.unibl.etf.pj.granicniprelaz.exception;
    exports  org.unibl.etf.pj.granicniprelaz.util;
    exports org.unibl.etf.pj.granicniprelaz.controllers;
    opens org.unibl.etf.pj.granicniprelaz.controllers to javafx.controls, javafx.fxml;


}