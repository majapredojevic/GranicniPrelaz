package org.unibl.etf.pj.granicniprelaz.exception;

public class FileLoadingException extends Exception {
    public static final String MSG = "File failed to load.";

    public FileLoadingException() {
        super(MSG);
    }
}
