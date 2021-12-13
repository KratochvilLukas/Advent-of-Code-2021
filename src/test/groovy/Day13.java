import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day13 {

    @Test
    public void day13_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day13.txt").toURI());
        Input input = new Input(i);
        input.foldFirst();

        System.out.println(input.visibleDots());
    }

    @Test
    public void day13_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day13.txt").toURI());
        Input input = new Input(i);
        input.foldAll();

        input.write();
    }

    private class Input {

        private int[][] grid;
        private List<Fold> folds;
        private int xMax;
        private int yMax;

        public Input(List<String> s) {
            List<String> dots = s.stream()
                    .filter(row -> !row.startsWith("fold") && !row.isEmpty())
                    .collect(Collectors.toList());
            folds = s.stream()
                    .filter(row -> row.startsWith("fold"))
                    .map(Fold::new)
                    .collect(Collectors.toList());
            xMax = dots.stream()
                    .map(row -> Integer.parseInt(row.split(",")[0]))
                    .max(Integer::compareTo).get();
            yMax = dots.stream()
                    .map(row -> Integer.parseInt(row.split(",")[1]))
                    .max(Integer::compareTo).get();
            grid = new int[yMax + 1][xMax + 1];
            for (String s1 : dots) {
                String[] coord = s1.split(",");
                grid[Integer.parseInt(coord[1])][Integer.parseInt(coord[0])] = 1;
            }
        }

        public void foldAll() {
            folds.forEach(this::doFold);
        }

        private void doFold(Fold fold) {
            if (fold.axis.equals("x")) {
                foldX(fold);
            } else {
                foldY(fold);
            }
        }

        private void foldFirst() {
            Fold fold = folds.get(0);
            doFold(fold);
        }

        private void foldX(Fold fold) {
            int xLength = getGridLength(xMax, fold.index);
            int[][] newGrid = new int[yMax + 1][xLength + 1];
            for (int y = 0; y <= yMax; y++) {
                for (int x = 1; x <= xLength; x++) {
                    int leftPosition = fold.index - x;
                    if (leftPosition >= 0) {
                        newGrid[y][xLength - x] = (grid[y][leftPosition] == 1 || (fold.index + x <= xMax && grid[y][fold.index + x] == 1)) ? 1 : 0;
                    } else {
                        newGrid[y][x] = grid[y][fold.index + x];
                    }
                }
            }
            xMax = xLength - 1;
            grid = newGrid;

        }

        private void foldY(Fold fold) {
            int yLength = getGridLength(yMax, fold.index);
            int[][] newGrid = new int[yLength + 1][xMax + 1];

            for (int y = 1; y <= yLength; y++) {
                for (int x = 0; x <= xMax; x++) {
                    int upPosition = fold.index - y;
                    if (upPosition >= 0) {
                        newGrid[yLength - y][x] = (grid[upPosition][x] == 1 || (fold.index + y <= yMax && grid[fold.index + y][x] == 1)) ? 1 : 0;
                    } else {
                        newGrid[y][x] = grid[fold.index + y][x];
                    }
                }
            }
            yMax = yLength - 1;
            grid = newGrid;
        }

        private int getGridLength(int originalMax, Integer foldIndex) {
            return ((double) originalMax / 2) < foldIndex ? foldIndex : originalMax - foldIndex;
        }

        public int visibleDots() {
            int sum = 0;
            for (int y = 0; y <= yMax; y++) {
                for (int x = 0; x <= xMax; x++) {
                    sum += grid[y][x];
                }
            }
            return sum;
        }

        public void write() {
            for (int y = 0; y <= yMax; y++) {
                StringBuilder row = new StringBuilder();
                for (int x = 0; x <= xMax; x++) {
                    if (grid[y][x] == 1) {
                        row.append("#");
                    } else {
                        row.append(" ");
                    }
                }
                System.out.println(row);
            }
        }
    }

    private class Fold {
        private String axis;
        private Integer index;

        private Fold(String input) {
            String[] i = input.replace("fold along ", "").split("=");
            axis = i[0];
            index = Integer.parseInt(i[1]);
        }
    }
}
