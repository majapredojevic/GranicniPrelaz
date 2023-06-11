package org.unibl.etf.pj.granicniprelaz.util;

import java.io.File;
import java.nio.file.Paths;

public abstract class Utils {

    public static void createFolderIfNotExists(String path) {
        File folder = Paths.get(path).toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }



}
