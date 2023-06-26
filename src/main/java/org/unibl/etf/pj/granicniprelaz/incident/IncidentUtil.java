package org.unibl.etf.pj.granicniprelaz.incident;

import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.exception.FileLoadingException;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;
import org.unibl.etf.pj.granicniprelaz.util.SimulationLogger;
import org.unibl.etf.pj.granicniprelaz.util.Utils;

import java.io.*;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public abstract class IncidentUtil {

    public static void writeListOfPunishedPersonsIntoFile(ListOfPunishedPersons listOfPunishedPersons) {

        Utils.createFolderIfNotExists(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        String fileName = "KaznjeneOsobe" + sdf.format(new Date());

        try (var objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + fileName + ".ser"))) {
            objectOutputStream.writeObject(listOfPunishedPersons);
        } catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }

    public static void writeCustomsIncident() {

        Utils.createFolderIfNotExists(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        String fileName = "KaznjenaVozila" + sdf.format(new Date());

        try {
            PrintWriter out = new PrintWriter(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY + fileName + ".txt");
            for(String incidentInfo: Simulation.listOfVehiclesWithIncidentAtCustoms) {
                out.println(incidentInfo);
            }
            out.close();
        }
        catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }


    public static ListOfPunishedPersons readListOfPunishedPersonsFromFile(String fileName) {

        if(fileName == null) {
            return null;
        }
        ListOfPunishedPersons list = new ListOfPunishedPersons();

        try (var objectInputStream = new ObjectInputStream(new FileInputStream(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + fileName))) {
            list = (ListOfPunishedPersons) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException exception) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return list;
    }

    public static String readContentFromFile(String fileName) {

        Path path = FileSystems.getDefault().getPath(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY + fileName);
        if(fileName == null) {
            return null;
        }
        List<String> content = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        try {
            content = Files.readAllLines(path);
        } catch (IOException exception) {
            SimulationLogger.log(IncidentUtil.class,Level.SEVERE,exception.getMessage(),exception);
        }
        for(String c: content) {
            contentBuilder.append(c).append("\n");
        }
       return contentBuilder.toString();
    }

    public static List<String> loadFilesList(String directory) throws FileLoadingException {

        List<String> listOfFiles = new ArrayList<>();
        File[] files = Paths.get(directory).toFile().listFiles();
        if (files == null) {
            throw new FileLoadingException();
        }
        for (File f : files)
            listOfFiles.add(f.getName());
        return listOfFiles;
    }

}

