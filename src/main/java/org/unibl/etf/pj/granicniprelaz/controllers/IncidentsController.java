package org.unibl.etf.pj.granicniprelaz.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.exception.FileLoadingException;
import org.unibl.etf.pj.granicniprelaz.incident.IncidentUtil;
import org.unibl.etf.pj.granicniprelaz.incident.ListOfPunishedPersons;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class IncidentsController implements Initializable {
    @FXML
    private ListView<String> lvPunishedPersonsFilesList;
    @FXML
    private ListView<String> lvCustomsFilesList;
    @FXML
    private Label lblListOfPunishedPersons;
    @FXML
    private Label lblCustomsInfo;


    private final ObservableList<String> policeFilesObservableList = FXCollections.observableList(new ArrayList<>());
    private final ObservableList<String> customsFilesObservableList = FXCollections.observableList(new ArrayList<>());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblListOfPunishedPersons.setText("");
        lblCustomsInfo.setText("");
        try {
            policeFilesObservableList.addAll(IncidentUtil.loadFilesList(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY));
            lvPunishedPersonsFilesList.setItems(policeFilesObservableList);

            customsFilesObservableList.addAll(IncidentUtil.loadFilesList(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY));
            lvCustomsFilesList.setItems(customsFilesObservableList);
        } catch (FileLoadingException exception) {
            SimulationLogger.log(IncidentsController.class, Level.SEVERE, exception.getMessage(), exception);
        }
    }

    @FXML
    void onListViewPunishedPersonsItemClicked(MouseEvent event) {
        String fileName = lvPunishedPersonsFilesList.getSelectionModel().getSelectedItem();
        ListOfPunishedPersons punishedPersons = IncidentUtil.readListOfPunishedPersonsFromFile(fileName);
        if (punishedPersons != null)
            Platform.runLater(() -> lblListOfPunishedPersons.setText(punishedPersons.toString()));
    }

    @FXML
    void onListViewCustomsFilesItemClicked(MouseEvent event) {
        String fileName = lvCustomsFilesList.getSelectionModel().getSelectedItem();
        String content = IncidentUtil.readContentFromFile(fileName);
        if (content != null)
            Platform.runLater(() -> lblCustomsInfo.setText(content));
    }

}
