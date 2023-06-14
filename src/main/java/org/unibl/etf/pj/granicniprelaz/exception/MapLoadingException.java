package org.unibl.etf.pj.granicniprelaz.exception;

public class MapLoadingException extends Exception
{
    public static final String MSG = "Path on map loading failed.";

    public MapLoadingException()
    {
        super(MSG);
    }

}
