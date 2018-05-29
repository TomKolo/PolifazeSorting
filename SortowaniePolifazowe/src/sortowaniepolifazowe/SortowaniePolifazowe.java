/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortowaniepolifazowe;

import GUI.MainWindow;
import Logic.Controller;
import Logic.Record;
import Logic.Tape;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author tomas
 */
public class SortowaniePolifazowe extends Application {

    private MainWindow MainWindow;
    public Controller control;
    @Override
    public void start(Stage primaryStage) throws IOException {
        control = new Controller();
        MainWindow = new MainWindow(primaryStage, this,control);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
