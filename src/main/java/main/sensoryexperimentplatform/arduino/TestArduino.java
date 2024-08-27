package main.sensoryexperimentplatform.arduino;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fazecast.jSerialComm.SerialPort;

public class TestArduino {
    private static SerialPort comPort;
    private static long startTime;
    private static StringBuilder serialBuffer = new StringBuilder();
    private static final double CALIBRATION_FACTOR1 = -418045;
    private static final double CALIBRATION_FACTOR2 = -437885;
    private static final double CALIBRATION_FACTOR3 = 0;
    private static final double CALIBRATION_FACTOR4 = 0;

    // List to store recorded data
    private static List<Double> recordedWeights = new ArrayList<>();

    // BufferedWriter to write data to file
    private static BufferedWriter writer;

    // Method to start recording data from the Arduino
    public static void startRecording() {
        comPort = SerialPort.getCommPort("/COM4"); // Update this to the correct port
        if (comPort == null) {
            System.out.println("COM not found");
            return;
        }

        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            System.out.println("Port connected successfully!");
        } else {
            System.out.println("Unable to connect");
            return;
        }

        try {
            Thread.sleep(2000); // 2 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Record the start time
        startTime = System.currentTimeMillis();

        // Initialize BufferedWriter
        try {
            writer = new BufferedWriter(new FileWriter("src/main/java/main/sensoryexperimentplatform/arduino/scale_data.txt", true)); // Append to file


        } catch (IOException e) {
            System.out.println("Failed to open file for writing.");
            e.printStackTrace();
        }

        comPort.addDataListener(new com.fazecast.jSerialComm.SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            public void serialEvent(com.fazecast.jSerialComm.SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                byte[] newData = new byte[comPort.bytesAvailable()];
                comPort.readBytes(newData, newData.length);

                serialBuffer.append(new String(newData));

                int index;
                while ((index = serialBuffer.indexOf("\n")) != -1) {
                    String completeLine = serialBuffer.substring(0, index).trim();
                    serialBuffer.delete(0, index + 1);
                    processReceivedData(completeLine);
                }
            }

            private void processReceivedData(String receivedData) {

                System.out.println("Received: " + receivedData);

                if (receivedData.startsWith("S1:") || receivedData.startsWith("S2:")){
                    String[] parts = receivedData.split(" ");
                    try {
                        double rawReading = Double.parseDouble(parts[1]);
                        double weight = 0;
                        int scaleNumber = 0;

                        if (receivedData.startsWith("S1:")){
                            scaleNumber = 1;
                            weight = rawReading * CALIBRATION_FACTOR1;
                        }
                        if (receivedData.startsWith("S2:")){
                            scaleNumber = 2;
                            weight = rawReading * CALIBRATION_FACTOR2;
                        }
                        if (receivedData.startsWith("S3:")){
                            scaleNumber = 3;
                            weight = rawReading * CALIBRATION_FACTOR3;
                        }
                        if (receivedData.startsWith("S4:")){
                            scaleNumber = 4;
                            weight = rawReading * CALIBRATION_FACTOR4;
                        }

                        recordedWeights.add(rawReading);
                       // String output = "Scale " + scaleNumber + ": " + rawReading + " kg";
                        String output = "Scale " + scaleNumber + ": " + weight + " kg";

                        // Write to file instead of printing to console
                        writer.write(output);
                        writer.newLine();
                        writer.flush(); // Make sure data is written immediately
                    } catch (Exception e) {
                        System.out.println("Failed to parse received data " + receivedData);
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            comPort.getOutputStream().write("s\n".getBytes());
            comPort.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop recording data from the Arduino
    public static void stopRecording() {
        try {
            comPort.getOutputStream().write("e\n".getBytes());
            comPort.getOutputStream().flush();

            if (comPort != null) {
                comPort.closePort();
            }

            // Close the writer
            if (writer != null) {
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve recorded weights
    public static List<Double> getRecordedWeights() {
        return recordedWeights;
    }
}
