package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {

    public  static SimulationController simulationController;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(Constants.SIMULATION_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        simulationController = fxmlLoader.getController();

        stage.setTitle(Constants.START_TITLE);

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.ICON_IMAGE_PATH))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}