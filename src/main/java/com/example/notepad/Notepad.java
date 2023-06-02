package com.example.notepad;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Notepad extends Application {
    public static Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuBar topMenuBar = new MenuBar();
        TextArea textArea = new TextArea();
        Button saveBtn = new Button("SAve");
        Menu fileMenu = new FileMenu(textArea,saveBtn);
        fileMenu.setText("File");
        Menu editMenu = new EditMenu();
        editMenu.setText("Edit");
        Menu searchMenu = new Menu("Search");
        Menu viewMenu = new Menu("View ");
        Menu encodingMenu = new Menu("Encoding");
        Menu languageMenu = new Menu("Language");
        Menu settingsMenu = new Menu("Settings");
        Menu toolsMenu = new Menu("Tools");
        Menu windowMenu = new Menu("Window");
        topMenuBar.getMenus().addAll(fileMenu,editMenu,searchMenu,viewMenu,encodingMenu,languageMenu,settingsMenu,toolsMenu,windowMenu);
        VBox vBox = new VBox();
        textArea.setMinSize(500,500);
        textArea.setStyle("-fx-text-fill: blue;");
        label = new Label();
        label.setText("1");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label,textArea);
        label.setPrefWidth(30);
        label.setTranslateY(3);
        textArea.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        label.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        label.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        label.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        label.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        label.setFont(Font.font("cursive", FontWeight.BOLD, FontPosture.ITALIC,20));
        ScrollPane scrollPane = new ScrollPane(hBox);
        vBox.getChildren().addAll(topMenuBar,scrollPane,saveBtn);
        Scene scene =  new Scene(vBox,800,600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        textArea.prefWidthProperty().bind(scene.widthProperty().subtract(20));
        textArea.prefHeightProperty().bind(scene.heightProperty().subtract(10));
        stage.setScene(scene);
        label.setStyle("-fx-control-inner-background: blue;");
        scene.setFill(Color.BLUEVIOLET);
        stage.show();
        Image image = new Image(getClass().getResource("icon.png").openStream());
        stage.getIcons().add(image);
    }
}
class FileMenu extends Menu{
    Menu fileMenu = new Menu("File");
    MenuItem newItem ;
    MenuItem openItem;
    MenuItem openFolderItem;
    MenuItem saveItem ;
    MenuItem saveAsItem = new MenuItem("Save as            CTRL+Shift+S");
    MenuItem renameItem = new MenuItem("Rename            CTRL+N");
    MenuItem closeItem = new MenuItem("Close            CTRL+N");
    MenuItem closeAllItem = new MenuItem("Close All            CTRL+N");
    MenuItem printItem = new MenuItem("Print            CTRL+N");
    MenuItem printNowItem = new MenuItem("Print now            CTRL+N");
    MenuItem exitItem = new MenuItem("Exit            CTRL+N");
    private int count = 0;
  public  FileMenu(TextArea textArea,Button saveBtn){
      this.fileMenu.setStyle("-fx-font-size:20; ");
      this.newItem = new MenuItem("New            CTRL+N");
      this.openItem = new MenuItem("Open           CTRL+O");
      this.openFolderItem = new MenuItem("Open folder     CTRL+N");
      this.saveItem = new MenuItem(String.format("Save  %-15s","CTRL+S"));
      this.saveAsItem = new MenuItem("Save as     CTRL+Shift+S");
      this.renameItem = new MenuItem("Rename       CTRL+N");
      this.closeItem = new MenuItem("Close        CTRL+N");
      this.closeAllItem = new MenuItem("Close All    CTRL+N");
      this.printItem = new MenuItem("Print      CTRL+N");
      this.printNowItem = new MenuItem("Print now     CTRL+N");
      this.exitItem = new MenuItem("Exit        CTRL+N");
      this.getItems().addAll(this.newItem,this.openItem,this.openFolderItem,this.saveItem,this.saveAsItem,this.renameItem,this.closeItem,this.closeAllItem,this.printItem,this.printNowItem,this.exitItem);
      this.handleSaveItem(textArea,saveBtn);
      this.newItem.setOnAction(e->{
          textArea.clear();
      });
      this.exitItem.setOnAction(e->{
          Platform.exit();
      });
      this.handaleOpen(textArea);
      textArea.setOnKeyPressed(event -> {
          if (event.getCode().toString().equals("ENTER")) {
              count++;
              String[] label = Notepad.label.getText().split("\n");
              String[] text = textArea.getText().split("\n");
              System.out.println(text.length +" " +label.length);
              if (count>=label.length) {
                  int message = Integer.parseInt(label[label.length - 1]) + 1;
                  Notepad.label.setText(Notepad.label.getText() + "\n" + message);
              }
          }
      });
  }
    public void handleSaveItem(TextArea textArea,Button saveBtn){
      this.saveItem.setOnAction(e->{
          String text = textArea.getText();

          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Save Text");
          fileChooser.setInitialFileName("output.txt");

          // Add individual extension filters
          FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
          FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
          FileChooser.ExtensionFilter docFilter = new FileChooser.ExtensionFilter("Word Documents (*.docx)", "*.docx");
          FileChooser.ExtensionFilter javaFilter = new FileChooser.ExtensionFilter("Java files(*.java)", "*.java");

          fileChooser.getExtensionFilters().addAll(txtFilter, csvFilter, docFilter,javaFilter);

          Stage stage = (Stage) saveBtn.getScene().getWindow();
          File file = fileChooser.showSaveDialog(stage);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException es) {
            System.out.println("An error occurred while saving the text to the file: " + es.getMessage());
        }
      });
    }
    public void handaleOpen(TextArea textArea){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Button openButton = new Button("Open");
        this.openItem.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                try {
                    String content = readFile(selectedFile);
                    textArea.setText(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
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

}
 class EditMenu extends Menu{
     public MenuItem undo;
    public MenuItem redo;
    public MenuItem cut;
    public MenuItem copy;
    public MenuItem paste;
    public MenuItem delete;
    public MenuItem selectAll;
    public MenuItem beginEndSelect;
    public Menu insert;
    public Menu copyClipBoard;
    public Menu indent;
    public Menu caseConvert;
    public Menu commentUncomment;
    public Menu autoCompletion;
    public Menu EOlConversion;
    public Menu blankOperation;
    public Menu pasteSpatial;
    public Menu onSelection;
    public MenuItem columnMode;
    public MenuItem  columnEditor;
    public MenuItem characterPanel;
    public MenuItem clipBoard;
    public MenuItem setReadOnly;
    public MenuItem clearReadonly;
    public EditMenu  (){
        this.undo = new MenuItem("Undo");
        this.redo = new MenuItem("Redo");
        this.cut = new MenuItem("Cut");
        this.copy = new MenuItem("Copy");
        this.paste = new MenuItem("Paste");
        this.delete = new MenuItem("Delete");
        this.selectAll = new MenuItem("Select All");
        this.beginEndSelect = new MenuItem("Begin /End Select");
        this.copyClipBoard = new Menu("Copy to clip board");
        this.indent = new Menu("Indent");
        this.caseConvert = new Menu("Case Convert");
        this.commentUncomment = new Menu("Comment /Uncomment");
        this.autoCompletion = new Menu("Auto completion");
        this.EOlConversion = new Menu("EOl Conversion");
        this.blankOperation = new Menu("Blank Operation");
        this.pasteSpatial= new Menu("Paste spatial");
        this.onSelection = new Menu("onSelection");
        this.columnMode = new MenuItem("Column mode");
        this.columnEditor = new MenuItem("Column Editor");
        this.characterPanel = new MenuItem("Character Panel");
        this.clipBoard = new MenuItem("Clip board History");
        this.setReadOnly = new MenuItem("Set read only");
        this.clearReadonly = new MenuItem("Clear Readonly");
        this.getItems().addAll(undo,redo,cut,copy,paste,delete,selectAll,beginEndSelect,copyClipBoard,indent,caseConvert,commentUncomment,autoCompletion,EOlConversion,blankOperation,pasteSpatial,onSelection,columnMode,columnEditor,characterPanel,clipBoard,setReadOnly,clearReadonly);
    }
}