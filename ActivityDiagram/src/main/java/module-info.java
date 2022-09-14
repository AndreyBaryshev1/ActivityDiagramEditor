/**
 * information for modules
 */
module org.bist.activitydiagram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens org.bist.activitydiagram to javafx.fxml;
    exports org.bist.activitydiagram;
}