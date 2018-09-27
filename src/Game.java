import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

import java.awt.*;

public class Game {

    private static final double SUDOKUSIZE = 500;
    private static final double CANVASWIDTH = SUDOKUSIZE * 1.1;
    private static final double CANVASHEIGHT = SUDOKUSIZE * 1.2;
    private static final double DOKUNUM = 16;
    private static final double CLEN = SUDOKUSIZE / DOKUNUM;
    private static final double MARGIN = (CANVASWIDTH - SUDOKUSIZE) * 0.5;
    private static ListView<Integer> dokuVals;
    private boolean clickBlock;
    Stage world;
    Group hexdoku;
    Scene gamestate;
    Board pan;

    public Game(Stage s) {
        world = s;
        hexdoku = new Group();
    }

    public void run() {
        pan = new Board();
        hexdoku = new Group();
        clickBlock = false;

        gamestate = new Scene(hexdoku, CANVASWIDTH, CANVASHEIGHT);
        gamestate.setFill(Color.ORANGE);

        setDokus();
        initBoard();

        world.setScene(gamestate);
    }

    private void changeScene() {
        world.setScene(gamestate);
    }

    private void setDokus() {
        ObservableList<Integer> dokus = FXCollections.observableArrayList();
        for (int i = 1; i <= DOKUNUM; i++) {
            dokus.add(i);
        }
        dokuVals = new ListView<>(dokus);
        dokuVals.setPrefWidth(CLEN * 2);
        dokuVals.setPrefHeight(CLEN * 3);
    }

    private void initBoard() {
        for (int x = 0 ; x < DOKUNUM; x++) {
            for (int y = 0; y < DOKUNUM; y++) {
                Rectangle cell = new Rectangle(x*CLEN + MARGIN, y*CLEN + MARGIN, CLEN, CLEN);
                cell.setFill(rectColor(x, y));
                Text value = makeInitText(x, y);
                int cellX = x;
                int cellY = y;
                EventHandler<MouseEvent> cellClicked = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mouseClickEvent("cellClickEvent", new int[]{cellX, cellY}, cell, value);
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, cellClicked);
                hexdoku.getChildren().add(cell);
            }
        }
        int divider = (int) Math.pow(DOKUNUM, 0.5);
        for (int x = 0; x <= DOKUNUM; x += divider) {
            Line vline = new Line();
            vline.setStartX(x*CLEN + MARGIN);
            vline.setEndX(x*CLEN + MARGIN);
            vline.setStartY(MARGIN);
            vline.setEndY(SUDOKUSIZE + MARGIN);
            hexdoku.getChildren().add(vline);
        }
        for (int y = 0; y <= DOKUNUM; y += divider) {
            Line hline = new Line();
            hline.setStartX(MARGIN);
            hline.setEndX(SUDOKUSIZE + MARGIN);
            hline.setStartY(y*CLEN + MARGIN);
            hline.setEndY(y*CLEN + MARGIN);
            hexdoku.getChildren().add(hline);
        }
    }

    private Text makeInitText(int x, int y) {
        Text txt = new Text(x*CLEN + MARGIN + CLEN / 3, y*CLEN + MARGIN + CLEN / 1.5, "");
        txt.setFont(new Font(CLEN / 2));
        hexdoku.getChildren().add(txt);
        return txt;
    }

    private void setCellValue(int x, int y, Text txt, int v) {
        pan.setCellValue(x, y, v);
        txt.setText(Integer.toString(v));
        txt.toFront();
        checkDone();
    }

    private Color rectColor (int x, int y) {
        if (x % 2 == 0 && y % 2 == 0) {
            return Color.GREENYELLOW;
        } else if (x % 2 == 1 && y % 2 == 1){
            return Color.GREENYELLOW;
        } else {
            return Color.LEMONCHIFFON;
        }
    }

    private void mouseClickEvent(String name, int[] args, Rectangle cell, Text txt) {
        if (clickBlock == true) {
            return;
        }
        if (name.equals("cellClickEvent")) {
            cellClickEvent(args[0], args[1], cell, txt);
        }
    }

    private void cellClickEvent(int x, int y, Rectangle cell, Text txt) {
        clickBlock = true;
        double xPos = x * CLEN + MARGIN;
        double yPos = y * CLEN + MARGIN;
        if (x < DOKUNUM / 2) {
            if (y < DOKUNUM / 2) {
                dokuVals.setLayoutX(xPos + CLEN);
                dokuVals.setLayoutY(yPos + CLEN);
            } else {
                dokuVals.setLayoutX(xPos + CLEN);
                dokuVals.setLayoutY(yPos - CLEN);
            }
        } else {
            if (y < DOKUNUM / 2) {
                dokuVals.setLayoutX(xPos - 2*CLEN);
                dokuVals.setLayoutY(yPos + CLEN);
            } else {
                dokuVals.setLayoutX(xPos - 2*CLEN);
                dokuVals.setLayoutY(yPos - CLEN);
            }
        }
        dokuVals.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dokuVals.toFront();
                setCellValue(x, y, txt, dokuVals.getSelectionModel().getSelectedItem());
                hexdoku.getChildren().remove(dokuVals);
                clickBlock = false;
            }
        });
        hexdoku.getChildren().add(dokuVals);
    }

    private void checkDone() {
        if (pan.isDone()) {
            System.out.println("DONE");
        }
    }
}
