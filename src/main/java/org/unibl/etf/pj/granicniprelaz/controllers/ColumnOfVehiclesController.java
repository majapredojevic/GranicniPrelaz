package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.map.Field;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.Utils;
import org.unibl.etf.pj.granicniprelaz.vehicle.Bus;
import org.unibl.etf.pj.granicniprelaz.vehicle.Car;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ColumnOfVehiclesController implements Initializable {
    @FXML
    private GridPane gpColumnOfVehicles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPath();
    }

    public void initPath() {

        List<Field> pathFields = Simulation.pathWithTerminals.getPathFields();

        Utils.setWhiteLabelsIntoMap(pathFields, gpColumnOfVehicles);

        Field field;

        for(int i = Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP; i < Constants.NUMBER_OF_VEHICLES + Constants.START_INDEX; i++) {
           field = pathFields.get(i);
            Label fieldLabel = Utils.createLabel();

            if (field.isHasVehicle()) {
                if (field.getVehicle() instanceof Bus) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.GREEN);
                } else if (field.getVehicle() instanceof Car)
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.RED);
                else {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.BLUE);
                }
            } else {
                Utils.setLableBackgroundAndBorderColor(fieldLabel,"", Constants.GRAY);
            }
            gpColumnOfVehicles.add(fieldLabel, field.getYPosition(), field.getXPosition());
        }
    }
    }




