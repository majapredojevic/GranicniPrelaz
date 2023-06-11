module org.unibl.etf.pj2.granicniprelaz.granicniprelaz {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.unibl.etf.pj2.granicniprelaz.granicniprelaz to javafx.fxml;
    exports org.unibl.etf.pj2.granicniprelaz.granicniprelaz;
}