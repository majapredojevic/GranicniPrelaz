package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}