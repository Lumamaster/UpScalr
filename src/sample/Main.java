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
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GridPane grid = new GridPane();
        grid.setMinSize(500, 500);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        FileChooser filechooser = new FileChooser();

        Text maintext = new Text("Insert instructions here");

        TextField filepath = new TextField();

        TextField length = new TextField();
        TextField width = new TextField();

        TextField lengthscale = new TextField();
        TextField widthscale = new TextField();

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

        EventHandler<ActionEvent> selectfile = actionEvent -> {
            File selected = filechooser.showOpenDialog(primaryStage);
        };

        EventHandler<ActionEvent> loadpreset = actionEvent -> {
            File selected = filechooser.showOpenDialog(primaryStage);
        };

        EventHandler<ActionEvent> savepreset = actionEvent -> {
            File selected = filechooser.showOpenDialog(primaryStage);
        };

        EventHandler<ActionEvent> resize = actionEvent -> {
            /*File selected = new File(file);
            try {
                Image selectedimage = ImageIO.read(selected);
                Scanner scan = new Scanner(System.in);
                System.out.println("Width scaling:");
                double widthscale = scan.nextDouble();
                System.out.println("Height scaling:");
                double heightscale = scan.nextDouble();
                int originalheight = selectedimage.getHeight(null);
                int originalwidth = selectedimage.getWidth(null);
                int newheight = (int)(originalheight * heightscale);
                int newwidth = (int)(originalwidth * widthscale);
                Image resized = selectedimage.getScaledInstance(newwidth, newheight, Image.SCALE_FAST);
                BufferedImage buff = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_3BYTE_BGR);
                Graphics g = buff.getGraphics();
                FileDialog dialog2 = new FileDialog((Frame)null, "Select File to Save To");
                dialog2.setMode(FileDialog.SAVE);
                dialog2.setVisible(true);
                String output = dialog2.getDirectory() + dialog2.getFile();
                //result = fileChooser.showSaveDialog(frame);
                //if (result == JFileChooser.APPROVE_OPTION)
                {
                    File save = new File(output);
                    try
                    {
                        g.drawImage(resized, 0, 0, null);
                        ImageIO.write(buff, "png", save);
                        System.exit(0);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid file chosen.");
                e.printStackTrace();
            }*/
        };

        primaryStage.setTitle("UpScalr");
        primaryStage.setScene(grid.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
