package main.sensoryexperimentplatform.arduino;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIControl {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Arduino Control");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setBounds(50, 50, 200, 30);
        frame.add(startButton);

        // Stop button
        JButton stopButton = new JButton("Stop");
        stopButton.setBounds(50, 100, 200, 30);
        frame.add(stopButton);

        // Action listener for the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestArduino.startRecording();
            }
        });

        // Action listener for the stop button
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestArduino.stopRecording();
            }
        });

        frame.setVisible(true);
    }
}
