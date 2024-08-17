package main.sensoryexperimentplatform;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class PortScannerGUI {
    private static final int DATA_RATE = 9600;
    private static final int TIME_OUT = 2000;
    private static final int CHECK_DURATION = 100; // 0,1 seconds

    private static PortInfo finalSelectedPort = null;
    private static CountDownLatch latch;

    public static void main(String[] args) {
        PortInfo selectedPortInfo = runPortScanner();
        if (selectedPortInfo != null) {
            System.out.println("Final Selected Port: " + selectedPortInfo.getPortName());
        } else {
            System.out.println("No port selected.");
        }
    }

    public static PortInfo runPortScanner() {
        latch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Serial Port Scanner");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            DefaultListModel<PortInfo> listModel = new DefaultListModel<>();
            JList<PortInfo> portList = new JList<>(listModel);
            portList.setCellRenderer(new PortCellRenderer());

            // Scan and populate the list of ports
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                boolean isActive = isPortActive(port);
                listModel.addElement(new PortInfo(port.getSystemPortName(), isActive));
            }

            JScrollPane scrollPane = new JScrollPane(portList);
            frame.add(scrollPane, BorderLayout.CENTER);

            portList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) { // This ensures the event is fired only once per selection
                    finalSelectedPort = portList.getSelectedValue();
                    if (finalSelectedPort != null) {
                        System.out.println("Selected Port: " + finalSelectedPort.getPortName());
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");

            saveButton.addActionListener(e -> {
                latch.countDown();  // Allow the main thread to proceed
                frame.dispose();    // Close the window
            });

            buttonPanel.add(saveButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });

        try {
            latch.await();  // Wait until the user selects a port and clicks "Save"
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return finalSelectedPort;
    }

    private static boolean isPortActive(SerialPort port) {
        AtomicBoolean isActive = new AtomicBoolean(false);

        try {
            port.setComPortParameters(DATA_RATE, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, TIME_OUT, 0);

            if (!port.openPort()) {
                System.err.println("Failed to open port: " + port.getSystemPortName());
                return false;
            }

            port.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                        isActive.set(true);
                    }
                }
            });

            // Wait for a specified duration to check for incoming data
            Thread.sleep(CHECK_DURATION);

            port.removeDataListener();
            port.closePort();

            return isActive.get();

        } catch (Exception e) {
            System.err.println("Error checking port: " + port.getSystemPortName());
            e.printStackTrace();
            if (port.isOpen()) {
                port.closePort();
            }
            return false;
        }
    }

    public static PortInfo getFinalSelectedPort() {
        return finalSelectedPort;
    }

    static class PortInfo {
        private final String portName;
        private final boolean isActive;

        public PortInfo(String portName, boolean isActive) {
            this.portName = portName;
            this.isActive = isActive;
        }

        public String getPortName() {
            return portName;
        }

        public boolean isActive() {
            return isActive;
        }

        @Override
        public String toString() {
            return portName;
        }
    }

    static class PortCellRenderer extends JCheckBox implements ListCellRenderer<PortInfo> {
        @Override
        public Component getListCellRendererComponent(JList<? extends PortInfo> list, PortInfo value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getPortName());
            setSelected(isSelected);

            if (isSelected) {
                setBackground(new Color(150, 150, 255)); // Blue color when selected
                setForeground(Color.WHITE);
            } else if (value.isActive()) {
                setBackground(new Color(200, 255, 200)); // Light green for active ports
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            setOpaque(true);
            return this;
        }
    }
}
