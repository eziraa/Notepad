package com.example.notepad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollableLabelExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a VBox to hold the content
        VBox vbox = new VBox();
        vbox.setPrefWidth(200); // Set the preferred width of the VBox

        // Create a label with a long text
        Label label = new Label("This is a long label that needs to be scrollable.");
        label.setWrapText(true); // Enable text wrapping

        // Add the label to the VBox
        vbox.getChildren().add(label);

        // Create a ScrollPane and set the VBox as its content
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally

        // Create a scene and set the ScrollPane as its root
        Scene scene = new Scene(scrollPane, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
