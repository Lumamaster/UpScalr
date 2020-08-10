package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    File current;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GridPane grid = new GridPane();
        //grid.setMinSize(500, 500);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        FileChooser filechooser = new FileChooser();

        Text maintext = new Text("Insert instructions here");

        TextField filepath = new TextField();
        filepath.setEditable(false);

        TextField length = new TextField();
        TextField width = new TextField();

        length.setPrefWidth(75);
        width.setPrefWidth(75);

        Text lengthtext = new Text("Image Length (px)");
        Text widthtext = new Text("Image Width (px)");

        Text lscaletext = new Text("Length Scale");
        Text wscaletext = new Text("Width Scale");

        TextField lscale = new TextField();
        TextField wscale = new TextField();

        lscale.setPrefWidth(75);
        wscale.setPrefWidth(75);

        CheckBox aspectratio = new CheckBox("Maintain Aspect Ratio");

        RadioButton bicubic = new RadioButton("High Interpolation (Cubic)");
        RadioButton bilinear = new RadioButton("Medium Interpolation (Linear)");
        RadioButton nearest = new RadioButton("No Interpolation (Nearest Neighbor)");
        ToggleGroup interpolation = new ToggleGroup();
        bicubic.setToggleGroup(interpolation);
        bilinear.setToggleGroup(interpolation);
        nearest.setToggleGroup(interpolation);

        Button generatebutton = new Button("Resize");
        Button openfile = new Button("Open image");
        Button loadpresetbutton = new Button("Load Preset");
        Button savepresetbutton = new Button("Save Preset");

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
            current = filechooser.showOpenDialog(primaryStage);
        };

        EventHandler<ActionEvent> loadpreset = actionEvent -> {
            File selected = filechooser.showOpenDialog(primaryStage);
            try {
                Image selectedimage = ImageIO.read(selected);
                length.setText(Integer.toString(selectedimage.getHeight(null)));
                width.setText(Integer.toString(selectedimage.getWidth(null)));
                lscale.setText("1.0");
                wscale.setText("1.0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        EventHandler<ActionEvent> savepreset = actionEvent -> {
            File selected = filechooser.showOpenDialog(primaryStage);
        };

        EventHandler<ActionEvent> resize = actionEvent -> {
            try {
                Image selectedimage = ImageIO.read(current);
                Image resized = selectedimage.getScaledInstance(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()), Image.SCALE_FAST);
                BufferedImage buff = new BufferedImage(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()), BufferedImage.TYPE_3BYTE_BGR);
                Graphics g = buff.getGraphics();
                File selected = filechooser.showSaveDialog(primaryStage);
                try {
                    g.drawImage(resized, 0, 0, null);
                    ImageIO.write(buff, "png", selected);
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid file chosen.");
                e.printStackTrace();
            }
        };

        primaryStage.setTitle("UpScalr");
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
