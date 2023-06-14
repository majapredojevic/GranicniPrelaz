package org.unibl.etf.pj.granicniprelaz.util;


import org.unibl.etf.pj.granicniprelaz.constants.Constants;
import org.unibl.etf.pj.granicniprelaz.controllers.SimulationController;
import org.unibl.etf.pj.granicniprelaz.controllers.StartApplication;
import org.unibl.etf.pj.granicniprelaz.simulation.Simulation;

import java.util.logging.Level;

public class TimeCounter extends Thread {
    private int time = 0;

    @Override
    public void run() {

//        while(!SimulationController.simulationFinished) {
//
//            synchronized (Simulation.queue) {
//                if (SimulationController.simulationPaused) {
//                    try {
//                        Simulation.queue.wait();
//                    } catch (InterruptedException exception) {
//                        SimulationLogger.log(getClass(), Level.SEVERE, exception.getMessage(), exception);
//                    }
//
//                }
//            }

            StartApplication.simulationController.setTimeLabel(time);


            try {
                Thread.sleep(Constants.WAITING_TIME);
            } catch (InterruptedException exception) {
                SimulationLogger.log(getClass(), Level.SEVERE,exception.getMessage(),exception);
            }

            time++;
        }
//    }

    public int getTime() {
        return time;
    }
}
