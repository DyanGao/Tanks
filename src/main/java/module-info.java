open module de.dywebstudio.tanks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.prefs;
    requires com.fasterxml.jackson.annotation;


    //opens de.dywebstudio.tanks to javafx.fxml;


    exports de.dywebstudio.tanks;
    exports de.dywebstudio.tanks.components;
}



