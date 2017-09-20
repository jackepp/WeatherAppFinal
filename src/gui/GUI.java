package gui;

/**
 * @author Jacob Permansson 2017-09-13
 *
 * This is the view. This is what the user sees and interacts with. This class creates a window and all compontents related to it.
 *
 */

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;


public class GUI extends JFrame{

    /**
     * the panel
     */
    private JPanel panel;
    /**
     * the controller object
     */
    private Controller controller;
    /**
     * The dropdown lists
     */
    private JComboBox<String> locationList, hoursList;
    /**
     * The string lists shown in the dropdown lists
     */
    private String[] stringHourList, locations;
    /**
     * the labels
     */
    private JLabel locationLabel, hoursLabel, cacheLabel;
    /**
     * the text fields
     */
    private JTextField showWeather, cacheMessage, cacheTextField;
    /**
     * the buttons
     */
    private JButton weatherButton, cacheButton;
    /**
     * the frame / window
     */
    private JFrame frame;

    /**
     * the constructor. This is created when a new GUI object is created.
     * @param controller the controller obj
     */
    public GUI(Controller controller) {
        this.controller = controller;
        createUI();
        frame = new JFrame();
        frame.setTitle("Weather App");
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(200, 200);
        frame.add(panel);
    }

    /**
     * Creates the interface.
     */

    private void createUI() {

        panel = new JPanel();
        panel.setSize(400, 250);
        panel.setLayout(null);


        // Create all the components

        locations = new String[]{"Skelleftea", "Kage", "Stockholm"};
        locationList = new JComboBox<>(locations);

        stringHourList = new String[24];
        for (int i = 0; i < 24; i++) {
            stringHourList[i] = Integer.toString(i);
        }

        hoursList = new JComboBox<>(stringHourList);
        locationLabel = new JLabel("Choose your location:");
        hoursLabel = new JLabel("What time?");

        showWeather = new JTextField("");
        showWeather.setEditable(false);
        weatherButton = new JButton("Show weather!");

        cacheButton = new JButton("Save");
        cacheLabel = new JLabel("Cache time format: YYYY-MM-DDThh:mm");
        cacheTextField = new JTextField();
        cacheMessage = new JTextField("");
        cacheMessage.setEditable(false);


        //Add components to the panel
        panel.add(locationLabel);
        panel.add(locationList);
        panel.add(hoursLabel);
        panel.add(hoursList);
        panel.add(weatherButton);
        panel.add(cacheTextField);
        panel.add(cacheLabel);
        panel.add(cacheButton);
        panel.add(cacheMessage);
        panel.add(showWeather);


        //Placements of the components on the panel

        Dimension size = locationLabel.getPreferredSize();
        locationLabel.setBounds(10, 15, size.width, size.height);

        size = locationList.getPreferredSize();
        locationList.setBounds(150, 10, size.width, size.height);

        size = hoursLabel.getPreferredSize();
        hoursLabel.setBounds(10 , 50 , size.width, size.height);

        size = hoursList.getPreferredSize();
        hoursList.setBounds(150 , 45 , size.width, size.height);

        size = cacheLabel.getPreferredSize();
        cacheLabel.setBounds(10, 135 , size.width, size.height);

        size = cacheTextField.getPreferredSize();
        cacheTextField.setBounds(10, 160 , size.width+120, size.height);

        size = weatherButton.getPreferredSize();
        weatherButton.setBounds(10, 100 , size.width, size.height);

        size = showWeather.getPreferredSize();
        showWeather.setBounds(150 , 105, size.width+ 50, size.height);

        size = cacheButton.getPreferredSize();
        cacheButton.setBounds(40 , 200 , size.width, size.height);

        size = cacheMessage.getPreferredSize();
        cacheMessage.setBounds(150 , 205 , size.width+ 170, size.height);

       // This happens when Show Weather button is pressed.

        weatherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                showWeather.setText(controller.searchTemperature(
                        locationList.getSelectedItem().toString(),
                        hoursList.getSelectedItem().toString()) + " C");
            }

        //handler for pressing update cache! It has to be correct format to be valid. Error message shown if thats the case.
        });
        cacheButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    controller.saveCacheTime(cacheTextField.getText());
                    cacheMessage.setText("Cache time saved.");
                } catch (DateTimeParseException e) {
                    cacheMessage.setText("Cache time not correct format.");
                }
            }
        });
    }
}
