package parser;

/**
 * @author Jacob Permansson 2017-09-10
 *
 * Location class uses for storing location information.
 */
public class Location {


    /**
     * name of a location
     */
    private String name;
    /**
     * the altitude for the location
     */
    private String altitude;
    /**
     * the latitude for the location
     */
    private String latitude;
    /**
     * the longitude for the location
     */
    private String longitude;

    /**
     * The constructor. Automatically saves values to these variables when a new Location is created.
     *
     * @param name location name
     * @param altitude location altitude
     * @param latitude location latitude
     * @param longitude location longitude
     *
     */

    protected Location(String name, String altitude, String latitude, String longitude){
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * to get the name of the location
     * @return location name
     */
    protected String getName(){
        return name;
    }

    /**
     * to get the location latitude
     * @return location latitude
     */
    protected String getLatitude(){
        return latitude;
    }

    /**
     * to get the location longitude
     *
     * @return location longitude
     */
    protected String getLongitude(){
        return longitude;
    }


}
