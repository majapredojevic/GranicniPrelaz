package org.unibl.etf.pj.granicniprelaz.constants;

import java.io.File;

public abstract class Constants {
    public static final String SIMULATION_VIEW = "simulation-view.fxml";
    public static final String COLUMN_OF_VEHICLES_VIEW = "column-of-vehicles-view.fxml";
    public static final String VEHICLE_VIEW = "vehicle-view.fxml";
    public static final String INCIDENTS_VIEW = "incidents-view.fxml";

    //index of terminal
    public static final int CUSTOMS_TERMINAL_1 = 0;
    public static final int CUSTOMS_TERMINAL_FOR_TRUCKS = 1;
    public static final int POLICE_TERMINAL_1 = 2;
    public static final int POLICE_TERMINAL_2 = 3;
    public static final int POLICE_TERMINAL_FOR_TRUCKS = 4;

    public static final int NUMBER_OF_ELEMENTS_AT_FIRST_MAP = 10;
    public static final  int NUMBER_OF_TERMINALS = 5;
    public static final int START_INDEX = 5;


    public static final int NUMBER_OF_VEHICLES = 50;
    public static final int NUMBER_OF_CARS = 35;
    public static final int NUMBER_OF_BUSSES = 5;
    public static final int NUMBER_OF_TRUCKS = 10;


    public static final int MAX_NUM_OF_PASSENGERS_IN_CAR = 5;
    public static final int MAX_NUM_OF_PASSENGERS_IN_BUS = 52;
    public static final int MAX_NUM_OF_PASSENGERS_IN_TRUCK = 3;


    public static final String START_TITLE = "Granični prelaz";



    public static final int WAITING_TIME = 1000;
    public static final int PASSENGER_PROCESSING_TIME = 500;
    public static final int PASSENGER_AT_BUS_PROCESSING_TIME = 100;
    public static final int CAR_WAITING_TIME_AT_CUSTOMS = 2000;


    public static final String DATE_TIME_FORMAT = "dd.MM.yy_hh_mm_ss";
    public static final String ICON_IMAGE_PATH= "icon.png";
    public static final String RESULTS_DIRECTORY = "." + File.separator + "res" + File.separator + "results" + File.separator;
    public static final String LIST_OF_PUNISHED_PERSONS_DIRECTORY = RESULTS_DIRECTORY + "persons" + File.separator;
    public static final String LIST_OF_PUNISHED_VEHICLES_DIRECTORY = RESULTS_DIRECTORY + "vehicles" + File.separator;
    public static final String TERMINALS_DIRECTORY = "." + File.separator + "res" + File.separator + "terminals" + File.separator;
    public static final String STATE_OF_TERMINALS_FILE = "StatusiTerminala.txt";
    public static final String PATH_ON_MAP = "." + File.separator + "res" + File.separator + "json" + File.separator + "path.json";



    //colors
    public static final String WHITE = "#FFFFFF";
    public static final String BLUE = "#00308F";
    public static final String GRAY = "#bbbbbb";
    public static final String RED = "ce3322";
    public static final String GREEN = "85af38";
    public static final String BRONZE = "#CD7F32";
    public static final String ORANGE = "#CC5500";
    public static final String POLICE_BLUE = "#374f6b";
    public static final String POLICE_TRUCK_BLUE = "#082759";



}
