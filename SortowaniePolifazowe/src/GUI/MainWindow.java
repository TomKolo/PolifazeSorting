/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Logic.Controller;
import Logic.Record;
import Logic.Tape;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JTextArea;
import sortowaniepolifazowe.SortowaniePolifazowe;

/**
 *
 * @author tomas
 */
public class MainWindow {

    SortowaniePolifazowe mainClass;
    Controller controller;
    private Stage primaryStage;
    private Button sortButton;
    private Button sortAllButton;
    private Button loadButton;
    private Button insertButton;
    private Button generateButton;
    private Button StartButton;
    private TextField generateTextField;
    private TextField XTextField;
    private TextField YTextField;
    private Text notSortedTextField1;
    private Text notSortedTextField2;
    private Text notSortedTextField3;
    private Text sortedTextField;

    private boolean start = false;
    private int sort_size = 0;

    public MainWindow(Stage stage, SortowaniePolifazowe mainClass, Controller contr) {
        primaryStage = stage;
        this.controller = contr;
        this.mainClass = mainClass;
        //Buttons
        Button btn1 = new Button();
        sortButton = btn1;
        btn1.setText("Sort step-by-step");
        btn1.setPrefWidth(200);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    sort_size++;
                    controller.sort();
                    controller.tape1.swap_to_read(0, 0);
                    controller.tape2.swap_to_read(0, 0);
                    controller.tape3.swap_to_read(0, 0);
                    notSortedTextField1.setText(controller.getText(1));
                    notSortedTextField2.setText(controller.getText(2));
                    notSortedTextField3.setText(controller.getText(3));
                    if (controller.konice_sortowania == true) {
                        btn1.setDisable(true);
                        controller.tape1.swap_to_read(0, 0);
                        controller.tape2.swap_to_read(0, 0);
                        controller.tape3.swap_to_read(0, 0);
                        if (controller.base_tape == 1) {
                            notSortedTextField1.setText(controller.getText(1));
                            notSortedTextField2.setText("");
                            notSortedTextField3.setText("");
                        } else if (controller.base_tape == 2) {
                            notSortedTextField2.setText(controller.getText(2));
                            notSortedTextField1.setText("");
                            notSortedTextField3.setText("");
                        } else if (controller.base_tape == 3) {
                            notSortedTextField3.setText(controller.getText(3));
                            notSortedTextField2.setText("");
                            notSortedTextField1.setText("");
                        } else {
                            notSortedTextField3.setText(controller.getText(3));
                            notSortedTextField2.setText(controller.getText(2));
                            notSortedTextField1.setText(controller.getText(1));
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Polifaza!");
                try {
                    sortedTextField.setText(get_info_text());
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btn2 = new Button();
        loadButton = btn2;
        btn2.setPrefWidth(100);
        btn2.setText("Load");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    sort_size = 0;
                    controller.tape1.liczba_odczytow = 0;
                    controller.tape1.liczba_zapisow = 0;
                    controller.offset_base = 0;
                    controller.already_used = 0;
                    btn1.setDisable(false);
                    sortAllButton.setDisable(false);
                    controller.konice_sortowania = false;
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    File path = fileChooser.showOpenDialog(primaryStage);
                    //System.out.println("WCZYTANIE :" + path.getAbsolutePath().replace("\\", "\\\\"));
                    controller.initialize(path.getAbsolutePath().replace("\\", "\\\\"), 2);
                    controller.first_distribute();
                    notSortedTextField1.setText(controller.getText(1));
                    notSortedTextField2.setText(controller.getText(2));
                    notSortedTextField3.setText(controller.getText(3));
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btn3 = new Button();
        insertButton = btn3;
        btn3.setPrefWidth(100);
        btn3.setText("Insert");
        btn3.setDisable(true);
        btn3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    double x = Double.parseDouble(XTextField.getText().trim());
                    double y = Double.parseDouble(YTextField.getText().trim());
                    //System.out.println("x = " + x + " y = " + y);
                    File f = new File("C:\\Users\\tomas\\Documents\\NetBeansProjects\\SortowaniePolifazowe\\text.txt");
                    controller.tape3.write_record(new Record(x, y), 0);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btn4 = new Button();
        generateButton = btn4;
        btn4.setPrefWidth(100);
        btn4.setText("Generate");
        btn4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    sort_size = 0;
                    controller.tape1.liczba_odczytow = 0;
                    controller.tape1.liczba_zapisow = 0;
                    controller.offset_base = 0;
                    controller.already_used = 0;
                    btn1.setDisable(false);
                    sortAllButton.setDisable(false);
                    controller.konice_sortowania = false;
                    //File f = new File("C:\\Users\\tomas\\Documents\\NetBeansProjects\\SortowaniePolifazowe\\text.txt");
                    // f.getParentFile().mkdirs();
                    // f.createNewFile();
                    System.out.println("Generating records");
                    //time to geneerate
                    int number_of_recorde_to_generate = Integer.parseInt(generateTextField.getText());
                    controller.initialize("C:\\Users\\tomas\\Documents\\NetBeansProjects\\SortowaniePolifazowe\\text", 1);
                    controller.tape3.swap_to_write();
                    Random r = new Random();
                    for (int i = 0; i < number_of_recorde_to_generate; i++) {
                        controller.tape3.write_record(new Record((double) (r.nextInt() % 1000), (double) (r.nextInt() % 1000)), 0);
                    }
                    controller.tape3.close_write(0);
                    controller.first_distribute();
                    notSortedTextField1.setText(controller.getText(1));
                    notSortedTextField2.setText(controller.getText(2));
                    notSortedTextField3.setText(controller.getText(3));
                    sortedTextField.setText(get_info_text());
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        Button btn5 = new Button();
        sortAllButton = btn5;
        btn5.setText("Sort all");
        btn5.setPrefWidth(150);
        btn5.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    controller.konice_sortowania = false;
                    while (controller.konice_sortowania == false) {
                        controller.sort();
                        sort_size++;

                    }

                    if (controller.konice_sortowania == true) {
                        btn1.setDisable(true);
                    }

                    if (controller.konice_sortowania == true) {
                        btn5.setDisable(true);
                    }
                    sortedTextField.setText(get_info_text());
                    controller.tape1.swap_to_read(0, 0);
                    controller.tape2.swap_to_read(0, 0);
                    controller.tape3.swap_to_read(0, 0);
                    if (controller.base_tape == 1) {
                        notSortedTextField1.setText(controller.getText(1));
                        notSortedTextField2.setText("");
                        notSortedTextField3.setText("");
                    } else if (controller.base_tape == 2) {
                        notSortedTextField2.setText(controller.getText(2));
                        notSortedTextField1.setText("");
                        notSortedTextField3.setText("");
                    } else if (controller.base_tape == 3) {
                        notSortedTextField3.setText(controller.getText(3));
                        notSortedTextField2.setText("");
                        notSortedTextField1.setText("");
                    } else {
                        notSortedTextField3.setText(controller.getText(3));
                        notSortedTextField2.setText(controller.getText(2));
                        notSortedTextField1.setText(controller.getText(1));
                    }

                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Polifaza!");
            }
        });
        ScrollPane sp = new ScrollPane();
        sp.setContent(notSortedTextField1);
        Button btn6 = new Button();
        StartButton = btn6;
        btn6.setPrefWidth(100);
        btn6.setText("Start Insert");
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (start == false) {
                        sort_size = 0;
                        controller.tape1.liczba_odczytow = 0;
                        controller.tape1.liczba_zapisow = 0;
                        insertButton.setDisable(false);
                        start = true;
                        controller.offset_base = 0;
                        controller.already_used = 0;
                        btn1.setDisable(false);
                        sortAllButton.setDisable(false);
                        controller.konice_sortowania = false;
                        controller.initialize("C:\\Users\\tomas\\Documents\\NetBeansProjects\\SortowaniePolifazowe\\text", 1);
                        controller.tape3.swap_to_write();
                        StartButton.setText("End Insert");
                    } else {
                        start = false;
                        insertButton.setDisable(true);
                        controller.tape3.close_write(0);
                        controller.first_distribute();
                        notSortedTextField1.setText(controller.getText(1));
                        notSortedTextField2.setText(controller.getText(2));
                        notSortedTextField3.setText(controller.getText(3));
                        StartButton.setText("Start Insert");
                        sortedTextField.setText(get_info_text());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        //TextFields
        TextField tfd5 = new TextField();
        generateTextField = tfd5;
        generateTextField.setPrefWidth(100);
        generateTextField.setPrefHeight(100);
        generateTextField.setText("");

        tfd5.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                tfd5.setText(oldValue);
            }
        });

        TextField x_double = new TextField();
        XTextField = x_double;
        generateTextField.setPrefWidth(100);
        generateTextField.setPrefHeight(100);
        generateTextField.setText("");

        TextField y_double = new TextField();
        YTextField = y_double;
        generateTextField.setPrefWidth(100);
        generateTextField.setPrefHeight(100);
        generateTextField.setText("");

        //Texts
        Text text1 = new Text("Dane:");

        text1.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text text2 = new Text("INFO:");

        text2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text text3 = new Text("Number of records to generate?");

        text3.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text tfd1 = new Text();
        notSortedTextField1 = tfd1;
        notSortedTextField1.setText("Tape1");

        Text tfd2 = new Text();
        notSortedTextField2 = tfd2;
        notSortedTextField2.setText("Tape2!");

        Text tfd3 = new Text();
        notSortedTextField3 = tfd3;
        notSortedTextField3.setText("Tape3!");

        Text tfd4 = new Text();
        sortedTextField = tfd4;
        sortedTextField.setText("");

        Text text_tape1 = new Text();
        text_tape1.setText("Tape 1");

        Text text_tape2 = new Text();
        text_tape2.setText("Tape 2");

        Text text_tape3 = new Text();
        text_tape3.setText("Tape 3");

        GridPane grid = new GridPane();

        grid.setHgap(5);
        grid.setVgap(5);

        grid.add(text3, 0, 1);
        grid.add(tfd5, 1, 1);
        grid.add(generateButton, 2, 1);
        grid.add(x_double, 1, 2);
        grid.add(y_double, 2, 2);
        grid.add(btn3, 3, 2);
        grid.add(StartButton, 4, 2);
        grid.add(text1, 0, 3);
        grid.add(text_tape1, 1, 3);
        grid.add(text_tape2, 2, 3);
        grid.add(text_tape3, 3, 3);
        grid.add(notSortedTextField1, 1, 4);
        grid.add(notSortedTextField2, 2, 4);
        grid.add(notSortedTextField3, 3, 4);
        grid.add(loadButton, 4, 4);
        grid.add(sortedTextField, 0, 2);
        grid.add(sortButton, 3, 1);
        grid.add(sortAllButton, 4, 1);
        Scene scene = new Scene(grid, 1000, 700);

        primaryStage.setTitle("Sortowanie Polifazowe Tomasz Kołodziej");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String get_info_text() throws IOException {
        return " Ilość faz scalania " + this.sort_size + ".\n Liczba zapisów do pliku " + this.controller.tape1.liczba_zapisow + ".\n Iiczba odczytów " + this.controller.tape1.liczba_odczytow + "." + "\n Posortowane Tape 1  " + controller.check_tape(1) + "\n Posortowane Tape 2  " + controller.check_tape(2) + "\n Posortowane Tape 3  " + controller.check_tape(3);
    }
}
