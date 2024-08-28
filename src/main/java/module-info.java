module main.sensoryexperimentplatform {
    requires javafx.controls; // JavaFX Controls
    requires javafx.fxml; // JavaFX FXML
    requires javafx.media; // JavaFX Media
    requires java.desktop; // For Swing and AWT
    requires org.controlsfx.controls; // ControlsFX
    requires com.dlsc.formsfx; // FormsFX
    requires org.kordamp.bootstrapfx.core; // BootstrapFX
    requires com.almasb.fxgl.all; // FXGL (verify this module name)
    requires com.fazecast.jSerialComm; // Serial communication

    // Open packages to JavaFX modules for FXML and other reflective access
    opens main.sensoryexperimentplatform to javafx.fxml;
    opens main.sensoryexperimentplatform.models to javafx.base;
    opens main.sensoryexperimentplatform.controllers to javafx.fxml;
    opens main.sensoryexperimentplatform.viewmodel to javafx.fxml;

    // Export packages for other modules or JavaFX
    exports main.sensoryexperimentplatform;
    exports main.sensoryexperimentplatform.controllers;
    exports main.sensoryexperimentplatform.viewmodel;
    exports main.sensoryexperimentplatform.arduino;
    opens main.sensoryexperimentplatform.arduino to javafx.fxml;
}
