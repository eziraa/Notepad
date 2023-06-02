package com.example.notepad;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class TextSaverFX extends Application {
    private TextArea textArea;
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Saver");

        textArea = new TextArea();
        saveButton = new Button("Save");
        saveButton.setOnAction(new SaveButtonHandler());

        VBox root = new VBox(10);
        root.getChildren().addAll(textArea, saveButton);

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    private class SaveButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String text = textArea.getText();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Text");
            fileChooser.setInitialFileName("output.txt");

            // Add individual extension filters
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
            FileChooser.ExtensionFilter docFilter = new FileChooser.ExtensionFilter("Word Documents (*.docx)", "*.docx");

            fileChooser.getExtensionFilters().addAll(txtFilter, csvFilter, docFilter);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                saveTextToFile(text, file);
                System.out.println("Text saved successfully!");
            }
        }
    }

    private void saveTextToFile(String text, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the text to the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
