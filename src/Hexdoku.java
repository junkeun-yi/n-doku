import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;


public class Hexdoku extends Application {

    private static final double SUDOKUSIZE = 500;
    private static final double CANVASWIDTH = SUDOKUSIZE * 1.1;
    private static final double CANVASHEIGHT = SUDOKUSIZE * 1.2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Line line = new Line();
        line.setStartX(50.0);
        line.setStartY(160.0);
        line.setEndX(500.0);
        line.setEndY(160.0);

        Text title = new Text();
        title.setFont(new Font(45));
        title.setX(50);
        title.setY(150);
        title.setText("Hexdoku");

        Button play = new Button();
        play.setText("Play");
        play.setLayoutX(400);
        play.setLayoutY(360);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame(primaryStage);
            }
        });

        Button leave = new Button();
        leave.setText("Leave");
        leave.setLayoutX(400);
        leave.setLayoutY(390);
        leave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
                System.exit(0);
            }
        });

        root.getChildren().addAll(line, title, play, leave);
        Scene scene = new Scene(root, CANVASWIDTH, CANVASHEIGHT);
        scene.setFill(Color.ORANGE);
        primaryStage.setTitle("Hexdoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) {
        Game game = new Game(primaryStage);
        game.run();
    }

}
