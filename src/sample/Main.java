package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends Application {

    File current;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        Text maintext = new Text("Insert instructions here");

        TextField filepath = new TextField();
        filepath.setEditable(false);

        TextField length = new TextField();
        TextField width = new TextField();

        length.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        width.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

        length.setEditable(false);
        width.setEditable(false);

        length.setPrefWidth(75);
        width.setPrefWidth(75);

        Text lengthtext = new Text("Image Length (px)");
        Text widthtext = new Text("Image Width (px)");

        Text lscaletext = new Text("Length Scale");
        Text wscaletext = new Text("Width Scale");

        TextField lscale = new TextField();
        TextField wscale = new TextField();

        lscale.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        wscale.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

        lscale.setEditable(false);
        wscale.setEditable(false);

        lscale.setPrefWidth(75);
        wscale.setPrefWidth(75);

        CheckBox aspectratio = new CheckBox("Maintain Aspect Ratio");

        RadioButton bicubic = new RadioButton("High Interpolation (Cubic)");
        RadioButton bilinear = new RadioButton("Medium Interpolation (Linear)");
        RadioButton nearest = new RadioButton("No Interpolation (Nearest Neighbor)");
        bicubic.setSelected(true);
        ToggleGroup interpolation = new ToggleGroup();
        bicubic.setToggleGroup(interpolation);
        bilinear.setToggleGroup(interpolation);
        nearest.setToggleGroup(interpolation);

        Button generatebutton = new Button("Resize");
        generatebutton.setDisable(true);
        Button openfile = new Button("Open image");
        Button loadpresetbutton = new Button("Load Preset");
        Button savepresetbutton = new Button("Save Preset");

        length.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                int oldnum = Integer.parseInt(oldValue);
                int newnum = Integer.parseInt(newValue);
                double change = newnum/oldnum;
                //TODO: ADD LISTENER TO LENGTH/WIDTH FIELDS AND AUTOADJUST FIELDS AS TEXT EDITED
                if (aspectratio.isSelected())
                {

                }
            }
        });

        grid.add(maintext, 0, 0, 3, 1);
        grid.add(filepath, 1, 1, 3, 1);
        grid.add(openfile, 0, 1);
        grid.add(lengthtext, 0, 2);
        grid.add(widthtext, 0, 3);
        grid.add(length, 1, 2);
        grid.add(width, 1, 3);
        grid.add(aspectratio, 4, 4, 2, 1);
        grid.add(lscaletext, 2, 2);
        grid.add(wscaletext, 2, 3);
        grid.add(lscale, 3, 2);
        grid.add(wscale, 3, 3);
        grid.add(bicubic, 4, 1, 2, 1);
        grid.add(bilinear, 4, 2, 2, 1);
        grid.add(nearest, 4, 3, 2, 1);
        grid.add(generatebutton, 0, 5);
        grid.add(savepresetbutton, 4, 5);
        grid.add(loadpresetbutton, 5, 5);

        EventHandler<ActionEvent> selectfile = actionEvent -> {
            FileChooser temp = new FileChooser();
            temp.setTitle("Select Image");
            temp.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            current = temp.showOpenDialog(primaryStage);
            if (current.exists()) {
                filepath.setText(current.getAbsolutePath());
                generatebutton.setDisable(false);
                try {
                    Image selectedimage = ImageIO.read(current);
                    length.setText(Integer.toString(selectedimage.getHeight(null)));
                    width.setText(Integer.toString(selectedimage.getWidth(null)));
                    length.setEditable(true);
                    width.setEditable(true);
                    lscale.setText("1.0");
                    wscale.setText("1.0");
                    lscale.setEditable(true);
                    wscale.setEditable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    displayError("An error occurred when attempting to open the selected image.");
                }
            }
        };

        EventHandler<ActionEvent> loadpreset = actionEvent -> {
            FileChooser temp = new FileChooser();
            temp.setTitle("Select Preset");
            temp.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("UpScalr Presets", "*.scalr")
            );
            current = temp.showOpenDialog(primaryStage);
            File selected = temp.showOpenDialog(primaryStage);
            try {
                Scanner scan = new Scanner(selected);
                String l = scan.nextLine();
                String w = scan.nextLine();
                String inter = scan.nextLine();
                lscale.setText(l);
                wscale.setText(w);
                switch (inter) {
                    case "1":
                        bicubic.setSelected(true);
                        bilinear.setSelected(false);
                        nearest.setSelected(false);
                        break;
                    case "2":
                        bicubic.setSelected(false);
                        bilinear.setSelected(true);
                        nearest.setSelected(false);
                        break;
                    default:
                        bicubic.setSelected(false);
                        bilinear.setSelected(false);
                        nearest.setSelected(true);
                        break;
                }
            } catch (Exception e) {
                displayError("An error occurred when attempting to open the selected preset.");
            }
        };

        EventHandler<ActionEvent> savepreset = actionEvent -> {
            FileChooser temp = new FileChooser();
            temp.setTitle("Save Preset");
            temp.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("UpScalr Presets", "*.scalr")
            );
            File selected = temp.showOpenDialog(primaryStage);
            try {
                PrintWriter out = new PrintWriter(selected);
                out.println(lscale.getText());
                out.println(wscale.getText());
                if (bicubic.isSelected()) {
                    out.println("1");
                } else if (bilinear.isSelected()) {
                    out.println("2");
                } else {
                    out.println("3");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                displayError("The specified file was not found.");
            }
        };

        EventHandler<ActionEvent> resize = actionEvent -> {
            try {
                Image selectedimage = ImageIO.read(current);
                int type = 1;
                boolean selected = true;
                if (bicubic.isSelected()) {
                    type = Image.SCALE_AREA_AVERAGING;
                } else if (bilinear.isSelected()) {
                    type = Image.SCALE_SMOOTH;
                } else if (nearest.isSelected()) {
                    type = Image.SCALE_FAST;
                } else {
                    selected = false;
                }
                if (selected) {
                    Image resized = selectedimage.getScaledInstance(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()), type);
                    BufferedImage buff = new BufferedImage(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()), BufferedImage.TYPE_3BYTE_BGR);
                    Graphics g = buff.getGraphics();
                    FileChooser temp = new FileChooser();
                    temp.setTitle("Save Image");
                    temp.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Images", "*.*"),
                            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                            new FileChooser.ExtensionFilter("PNG", "*.png")
                    );
                    File saveto = temp.showSaveDialog(primaryStage);
                    try {
                        g.drawImage(resized, 0, 0, null);
                        ImageIO.write(buff, "png", saveto);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setContentText("Resizing complete!");
                        alert.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    displayError("Please select a interpolation option before resizing.");
                }
            } catch (Exception e) {
                displayError("An invalid image was selected.");
                e.printStackTrace();
            }
        };

        generatebutton.setOnAction(resize);
        openfile.setOnAction(selectfile);
        savepresetbutton.setOnAction(savepreset);
        loadpresetbutton.setOnAction(loadpreset);

        primaryStage.setTitle("UpScalr");
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }

    public void displayError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.show();
    }
}
