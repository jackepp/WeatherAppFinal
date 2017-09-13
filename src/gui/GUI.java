package gui;

/**
 * @author Jacob Permansson 2017-09-13
 *
 * This is the view. This is what the user sees and interacts with. This class creates a window and all compontents related to it.
 *
 */

import reader.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;

public class GUI extends JFrame{

   private JPanel panel;
    Controller controller;

    public GUI(Controller controller) {
        this.controller = controller;

        createUI();
        JFrame frame = new JFrame();
        frame.setTitle("Weather App");
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(200, 200);
        frame.add(panel);
    }

    public void createUI() {

        panel = new JPanel();
        panel.setSize(400, 250);


        //Set layout manager
        panel.setLayout(null);
        //Create component

        JButton weatherButton = new JButton("Show weather!");
        JButton cacheButton = new JButton("Save");

        JLabel cacheLabel = new JLabel("Cache time format: YYYY-MM-DDThh:mm");


        JTextField cacheTextField = new JTextField();


        JTextField cacheMessage = new JTextField("");
        cacheMessage.setEditable(false);
        JTextField weather = new JTextField("");
        weather.setEditable(false);

        JComboBox hoursList;
        String[] stringHourList = new String[24];
        for (int i = 0; i < 24; i++) {
            stringHourList[i] = Integer.toString(i);
        }
        hoursList = new JComboBox(stringHourList);

        JComboBox locationList;
        String[] locations = {"Skelleftea", "Kage", "Stockholm"};
        locationList = new JComboBox(locations);

        JLabel locationLabel = new JLabel("Choose your location:");
        JLabel hoursLabel = new JLabel("What time?");


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
        panel.add(weather);

        //Location settings

        Dimension size = locationLabel.getPreferredSize();
        locationLabel.setBounds(10, 15, size.width, size.height);

        size = locationList.getPreferredSize();
        locationList.setBounds(150, 10, size.width, size.height);

        //Hours settings

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

        size = weather.getPreferredSize();
        weather.setBounds(150 , 105, size.width+ 50, size.height);

        size = cacheButton.getPreferredSize();
        cacheButton.setBounds(40 , 200 , size.width, size.height);

        size = cacheMessage.getPreferredSize();
        cacheMessage.setBounds(150 , 205 , size.width+ 170, size.height);




       // handler for pressing show weather!

        weatherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                weather.setText(controller.searchTemperature(
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
