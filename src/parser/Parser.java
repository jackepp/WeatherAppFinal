package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * @author Jacob Permansson 2017-09-13
 * This class is the model. Here does the saving and reading of data occur.
 *
 */

public class Parser {
    /**
     * The locations will be saved in this array.
     */
    private Location[] locations;
    /**
     * the date and with the format yyyy-MM-dd
     */
    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * an emptry string reserved for the temp
     */
    private String temp = "";

    /**
     * Constructor
     */
    public Parser(){
        parseLocations();
    }

    /**
     * Parsing the locations from places.xml*/

    private void parseLocations(){

        locations = new Location[3];

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("places.xml");
            doc.getDocumentElement().normalize();
            NodeList localityList = doc.getElementsByTagName("locality");

            for(int i=0;i<localityList.getLength();i++){
                Node l = localityList.item(i);
                if(l.getNodeType()==Node.ELEMENT_NODE){
                    Element locality = (Element) l;

                    locations[i] = new Location(
                            locality.getAttribute("name"),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("altitude").getNodeValue(),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("latitude").getNodeValue(),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("longitude").getNodeValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsing the temperature for the selected time
     * from the corresponding xml-file for the chosen location.
     *
     * @param name Selected location
     * @param time Selected time
     *
     * @return temp Desired temperature (String)
     *
     * */


    public String getTemp(String name, String time) throws NullPointerException {

        //This fixes the format of single digit time. 5:00 -> 05:00 for example.

        String newTime;
        if(Integer.parseInt(time) < 10){
            newTime = "0" + time + ":00";
        }
        else{
            newTime = time + ":00";
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            File xmlFile = new File(name + ".xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList timeList = doc.getElementsByTagName("time");

            //Getting tomorrows date
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            String dateOfTomorrow = date.format(dt);

            //Loops though the xml-file and returns the temp-value when it finds the correct temp

            for (int i = 0; i < timeList.getLength(); i++) {
                Node l = timeList.item(i);
                if (l.getNodeType() == Node.ELEMENT_NODE) {
                    Element timeElement = (Element) l;
                    String timeVar = dateOfTomorrow + "T" + newTime + ":00Z";
                    if (timeElement.getAttribute("to").equals(timeVar)&&(timeElement.getAttribute("to").equals(timeElement.getAttribute("from")))) {
                        temp = timeElement.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue();
                        return temp;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;

    }

    /**
     * Downloads the weather data in a xml-file through a REST-API.
     *
     * @param name The name the desired location
     *
     *
     * */

    public void downloadFromYr(String name) {
        Location location = null;

        System.out.println("Executing downloadFromYr");

        for (Location location1 : locations) {
            if (Objects.equals(location1.getName(), name)) {
                location = location1;
                System.out.println(location.getName());
            }
        }

        try {

            URL url = new URL("http://api.yr.no/weatherapi/locationforecast/1.9/?lat=" +
                    location.getLatitude() + ";lon=" + location.getLongitude());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            //System.out.println("Sending 'GET' requets to URL");
            //System.out.println("Response code:" + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            PrintWriter writer = new PrintWriter(location.getName() + ".xml", "UTF-8");
            writer.write(response.toString());
            writer.close();
            System.out.println("Requested data save to the fil in: " + location.getName() + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
