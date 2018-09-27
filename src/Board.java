import java.util.ArrayList;

public class Board {

    private class Cell {
        int value = -1;
        int guess = -1;
        Cell() {

        }
        void setValue(int v) {
            this.value = v;
        }
        int getValue() {
            return this.value;
        }
        void setGuess(int g) {
            this.guess = g;
        }
        int getGuess() {
            return this.guess;
        }
    }

    private static final int DOKUNUM = 16;
    private static final int DOKUNIT = (int) Math.pow(DOKUNUM, 0.5);
    private static final int DOKUSUM = (int) ((DOKUNUM) * (DOKUNUM + 1) * 0.5);
    ArrayList<ArrayList<Cell>> map = new ArrayList<>();
    private int count = 0;

    public Board() {
        for (int x = 0; x < DOKUNUM; x++) {
            map.add(new ArrayList<Cell>());
            for (int y = 0; y < DOKUNUM; y++) {
                map.get(x).add(new Cell());
            }
        }
    }

    public void setCellValue(int x, int y, int v) {
        if (map.get(x).get(y).getValue() == -1) {
            count++;
        }
        map.get(x).get(y).setValue(v);
    }

    public int getCellValue(int x, int y) {
        return map.get(x).get(y).getValue();
    }

    public void setCellGuess(int x, int y, int g) {
        map.get(x).get(y).setGuess(g);
    }

    public int getCellGuess(int x, int y) {
        return map.get(x).get(y).getGuess();
    }

    @SuppressWarnings("Duplicates")
    public boolean isDone() {
        if (count != DOKUNUM * DOKUNUM) {
            return false;
        }

        boolean solved = true;
        for (int x = 0; x < DOKUNUM; x++) {
            int sum = 0;
            for (int y = 0; y < DOKUNUM; y++) {
                sum += map.get(x).get(y).getValue();
            }
            solved = solved && (sum == DOKUSUM);
        }
        for (int y = 0; y < DOKUNUM; y++) {
            int sum = 0;
            for (int x = 0; x < DOKUNUM; x++) {
                sum += map.get(x).get(y).getValue();
            }
            solved = solved && (sum == DOKUSUM);
        }
        for (int ind = 0; ind < DOKUNUM; ind++) {
            int i = (int) (ind / DOKUNIT);
            int j = ind % DOKUNIT;
            int sum = 0;
            for (int x = i * DOKUNIT; x < i * DOKUNIT + DOKUNIT; x++) {
                for (int y = j * DOKUNIT; y < j * DOKUNIT + DOKUNIT; y++) {
                    sum += map.get(x).get(y).getValue();
                }
            }
            solved = solved && (sum == DOKUSUM);
        }
        return solved;
    }





}