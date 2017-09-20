package controller;

import parser.Parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


/**
 * @author Jacob Permansson 2017-09-13
 *
 * This class is the controller. Its sort of an intermediary between the view and the model.
 *
 */
public class Controller {
    private Parser parser;
    private LocalDateTime cacheTime = LocalDateTime.now();

    /**
     * Constructor. saves the attached parser object.
     * @param parser the parser obj
     */

    public Controller(Parser parser){
        this.parser = parser;
    }

    /**
    * Checks if data should be fetched from file or through a new HTTP-request to YR.
    *
     * @param time the chosen time
     * @param name the chosen location
     *
     * @return temp
     *
    * */
    public String searchTemperature(String name, String time) {
        String temp;
        LocalDateTime currentTime = LocalDateTime.now();
        if(currentTime.isAfter(cacheTime)) {
            parser.downloadFromYr(name);
            System.out.println("Temp from a newly downloaded file.");
            temp = parser.getTemp(name, time);
        } else {
            System.out.println("Temp from stored file.");
            temp = parser.getTemp(name,time);
        }
        return temp;
    }

    /** Saves the time for how long the weather should be fetched from the file.
     * @param cacheTime how long time the data should be cached
     *
     * */

    public void saveCacheTime(String cacheTime) throws DateTimeParseException {
        try{
            this.cacheTime = LocalDateTime.parse(cacheTime);
        } catch (DateTimeParseException e){
            throw e;
        }

    }

}
