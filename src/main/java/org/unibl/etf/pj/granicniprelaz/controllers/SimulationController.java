package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.util.TimeCounter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    private Simulation simulation;

    public static boolean simulationStarted;
    public static boolean simulationPaused;
    public static boolean simulationFinished;

    public static TimeCounter timeCounter;

    @FXML
    private Label lblTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        simulationStarted = false;
        simulationFinished = false;
        simulationPaused = false;

        simulation = new Simulation();
        lblTime.setText("0s");



    }

    @FXML
    void onBtnColumnOfVehiclesClicked(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(ColumnOfVehiclesController.class.getResource(Constants.COLUMN_OF_VEHICLES_VIEW));

            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setTitle(Constants.START_TITLE);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.ICON_IMAGE_PATH))));
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }

    }
    public void setTimeLabel(int timeCounter) {
        Platform.runLater(() -> lblTime.setText(timeCounter + "s"));
    }

    public int getTime() {
        return timeCounter.getTime();
    }

}