package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.exception.FileLoadingException;
import org.unibl.etf.pj.granicniprelaz.map.Field;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.terminal.CustomsTerminal;
import org.unibl.etf.pj.granicniprelaz.terminal.CustomsTerminalForTrucks;
import org.unibl.etf.pj.granicniprelaz.terminal.PoliceTerminalForTrucks;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.util.TerminalWatcher;
import org.unibl.etf.pj.granicniprelaz.util.TimeCounter;
import org.unibl.etf.pj.granicniprelaz.util.Utils;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.Car;
import org.unibl.etf.pj.granicniprelaz.vehicle.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class SimulationController implements Initializable {

    private Simulation simulation;
    public static ColumnOfVehiclesController columnOfVehiclesController;
    private final TerminalWatcher watcher = new TerminalWatcher();

    public static boolean simulationStarted;
    public static boolean simulationPaused;
    public static boolean simulationFinished;

    public static TimeCounter timeCounter;

    @FXML
    private ListView<Label> lvVehicles;
    @FXML
    private Label lblTerminalDescription;

    @FXML
    private GridPane gpColumnOfVehiclesWithTerminals;
    @FXML
    private Label lblTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            watcher.start();
            setSimulationController();
        } catch (FileLoadingException exception) {
            SimulationLogger.log(this.getClass(), Level.SEVERE, exception.getMessage(), exception);
            System.exit(1);
        }
    }

    private void setSimulationController() throws FileLoadingException {
        simulationStarted = false;
        simulationFinished = false;
        simulationPaused = false;

        simulation = new Simulation();
        lblTime.setText("0s");
        lblTerminalDescription.setText("");
        initPath();
        setListViewOfVehicles();
    }

    public void setTimeLabel(int timeCounter) {
        Platform.runLater(() -> lblTime.setText(timeCounter + "s"));
    }

    public void initPath() {
        List<Field> pathfields;
        synchronized (Simulation.pathWithTerminals) {
            Utils.checkSimulationPause();
            pathfields = Simulation.pathWithTerminals.getPathFields();
        }
        Utils.setWhiteLabelsIntoMapWithTerminals(pathfields, gpColumnOfVehiclesWithTerminals);
        Field field;
        for (int i = 0; i < 10; i++) {
            field = pathfields.get(i);

            Label fieldLabel = Utils.createLabel();

            if (field.isHasVehicle()) {
                if (field.getVehicle() instanceof Bus) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.GREEN);
                } else if (field.getVehicle() instanceof Car)
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.RED);
                else {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.BLUE);
                }
            } else if (field.isHasTerminal()) {
                if (field.getTerminal() instanceof CustomsTerminalForTrucks)
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getTerminal().getTerminalName(), Constants.ORANGE);
                else if (field.getTerminal() instanceof CustomsTerminal) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getTerminal().getTerminalName(), Constants.BRONZE);
                } else if (field.getTerminal() instanceof PoliceTerminalForTrucks) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getTerminal().getTerminalName(), Constants.POLICE_TRUCK_BLUE);

                } else {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getTerminal().getTerminalName(), Constants.POLICE_BLUE);
                }
            } else {
                Utils.setLableBackgroundAndBorderColor(fieldLabel, "", Constants.GRAY);

            }

            gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
        }
    }
    public void unselectListViewItem() {
        lvVehicles.getSelectionModel().clearSelection();
    }
    public void setLabelTerminalDescription(String description) {
        Platform.runLater(() -> lblTerminalDescription.setText(description));
    }

    private void setListViewOfVehicles() {
        lvVehicles.getItems().clear();
        for (int i = 0; i < Constants.NUMBER_OF_VEHICLES; i++) {
            Label labelVehicle = new Label("lblVehicle" + i + 1);
            Vehicle vehicle = Simulation.columnOfVehicles.get(i);
            Utils.setLabelListView(labelVehicle, vehicle.getVehicleName(), vehicle instanceof Car ? Constants.RED : (vehicle instanceof Bus ? Constants.GREEN : Constants.BLUE));
            lvVehicles.getItems().add(labelVehicle);
        }

    }

    @FXML
    public void onLvItemClicked(MouseEvent event) {
        if (lvVehicles.getSelectionModel().getSelectedIndex() != -1) {
            int vehicleIndex = lvVehicles.getSelectionModel().getSelectedIndex();
            Vehicle vehicle = Simulation.columnOfVehicles.get(vehicleIndex);
            //Open new stage
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(VehicleController.class.getResource(Constants.VEHICLE_VIEW));

                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 450, 450);
                VehicleController vehicleController = fxmlLoader.getController();
                vehicleController.initInfo(vehicle);

                stage.setTitle(Constants.START_TITLE);
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.ICON_IMAGE_PATH))));
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.setScene(scene);
                stage.show();
                stage.onCloseRequestProperty().set(event1 -> unselectListViewItem());
            } catch (IOException exception) {
                SimulationLogger.logAsync(getClass(), exception);
            }
        }
    }
    @FXML
    void onBtnColumnOfVehiclesClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ColumnOfVehiclesController.class.getResource(Constants.COLUMN_OF_VEHICLES_VIEW));

            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 500, 550);

            columnOfVehiclesController = fxmlLoader.getController();
            stage.setTitle(Constants.START_TITLE);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.ICON_IMAGE_PATH))));
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }
    }

    @FXML
    void onBtnRunStopClicked(ActionEvent event) {
        if (!simulationStarted) {
            if (simulationFinished) {
                try {
                    setSimulationController();
                } catch (FileLoadingException exception) {
                    SimulationLogger.log(this.getClass(), Level.SEVERE, exception.getMessage(), exception);
                }
            }
            timeCounter = new TimeCounter();
            simulation.start();
            timeCounter.start();
            simulationStarted = true;
        } else {
            simulationPaused = !simulationPaused;
            if (!simulationPaused) {
                synchronized (Simulation.pathWithTerminals) {
                    Simulation.pathWithTerminals.notifyAll();
                }

            }
        }
    }
    @FXML
    void onBtnIncidentsClicked(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(IncidentsController.class.getResource(Constants.INCIDENTS_VIEW));

            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setTitle(Constants.START_TITLE);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.ICON_IMAGE_PATH))));
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }
    }

}