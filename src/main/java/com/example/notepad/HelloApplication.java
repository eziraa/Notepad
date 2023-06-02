package com.example.notepad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        TextField textField = new TextField();
        Button btn =  new Button("save");
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(textField,btn);
        Scanner scanner = new Scanner(System.in);
        textField.setMinWidth(500);
        textField.setMinHeight(500);
        btn.setOnAction(es->{
                System.out.println("Enter the text to save:");
                String text = textField.getText();

                System.out.println("Enter the file path to save the text (e.g., 'C:\\output.txt'):");
                String filePath = scanner.next();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(text);
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the text to the file: " + e.getMessage());
                }
            System.out.println("Text saved successfully!");


        });
        Scene scene = new Scene(gridPane, 700, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}