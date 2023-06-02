package com.example.notepad;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileChooserExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Chooser Example");

        TextArea textArea = new TextArea();

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open File");

        // Set initial directory (optional)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Add extension filters if desired (optional)
        // fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Create an "Open" button to trigger the file chooser
        Button openButton = new Button("Open");
        openButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    String content = readFile(selectedFile);
                    textArea.setText(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(openButton);
        root.setCenter(textArea);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
