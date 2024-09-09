//package main.sensoryexperimentplatform.arduino;
//
//import com.fazecast.jSerialComm.SerialPort;
//import com.fazecast.jSerialComm.SerialPortDataListener;
//import com.fazecast.jSerialComm.SerialPortEvent;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//import main.sensoryexperimentplatform.models.Experiment;
//import main.sensoryexperimentplatform.viewmodel.PopUpVM;
//
//import java.io.IOException;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import static main.sensoryexperimentplatform.utilz.PopUpType.ERROR;
//
//public class PortScanner {
//    @FXML
//    private Button btn_back;
//    @FXML
//    private static ListView<PortInfo> portList;
//
//    private static final int DATA_RATE = 9600;
//    private static final int TIME_OUT = 2000;
//    private static final int CHECK_DURATION = 100; // 0,1 seconds
//
//    private static PortScannerGUI.PortInfo finalSelectedPort = null;
//    private static CountDownLatch latch;
//
//    private Experiment experiment;
//    private Stage ownerStage;
//
//    public void initPortScanner(Experiment experiment, Stage ownerStage) throws IOException {
//        this.experiment = experiment;
//        this.ownerStage = ownerStage;
//        populatePortList();
//
//    }
//    public void populatePortList() throws IOException {
//        // Scan and populate the list of ports
//        SerialPort[] ports = SerialPort.getCommPorts();
//        for (SerialPort port : ports) {
//            boolean isActive = isPortActive(port);
//            portList.getItems().add(new PortInfo(port.getSystemPortName(), isActive));
//        }
//    }
//
//    public static void main(String[] args) {
//        PortInfo selectedPortInfo = runPortScanner();
//        if (selectedPortInfo != null) {
//            System.out.println("Final Selected Port: " + selectedPortInfo.getPortName());
//        } else {
//            System.out.println("No port selected.");
//        }
//    }
//
//    public static PortInfo runPortScanner() {
//        latch = new CountDownLatch(1);
//
//
//    }
//
//    private static boolean isPortActive(SerialPort port) throws IOException {
//        AtomicBoolean isActive = new AtomicBoolean(false);
//
//        try {
//            port.setComPortParameters(DATA_RATE, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
//            port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, TIME_OUT, 0);
//
//            if (!port.openPort()) {
//                System.err.println("Failed to open port: " + port.getSystemPortName());
//                return false;
//            }
//
//            port.addDataListener(new SerialPortDataListener() {
//                @Override
//                public int getListeningEvents() {
//                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//                }
//
//                @Override
//                public void serialEvent(SerialPortEvent event) {
//                    if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                        isActive.set(true);
//                    }
//                }
//            });
//
//            // Wait for a specified duration to check for incoming data
//            Thread.sleep(CHECK_DURATION);
//
//            port.removeDataListener();
//            port.closePort();
//
//            return isActive.get();
//
//        } catch (Exception e) {
//            PopUpVM popUpError = new PopUpVM(ERROR, "Error checking port: " + port.getSystemPortName(), new Experiment(), new Stage() );
//            e.printStackTrace();
//            if (port.isOpen()) {
//                port.closePort();
//            }
//            return false;
//        }
//    }
//
//    @FXML
//    void cancel(ActionEvent event) {
//        Stage stage = (Stage) btn_back.getScene().getWindow();
//        stage.close();
//    }
//
//    @FXML
//    void connectPort(ActionEvent event) {
//        PortInfo selectedPort = portList.getSelectionModel().getSelectedItem();
//        if(selectedPort != null){
//
//        }
//    }
//
//    static class PortInfo {
//        private final String portName;
//        private final boolean isActive;
//
//        public PortInfo(String portName, boolean isActive) {
//            this.portName = portName;
//            this.isActive = isActive;
//        }
//
//        public String getPortName() {
//            return portName;
//        }
//
//        public boolean isActive() {
//            return isActive;
//        }
//
//        @Override
//        public String toString() {
//            return portName;
//        }
//    }
//
//
//
//
//}
