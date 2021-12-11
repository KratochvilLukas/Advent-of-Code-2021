import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

    @Test
    public void day11_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day11.txt").toURI());
        Input input = new Input(i);
        for (int it = 0; it < 100; it++) {
            input.step();
        }

        System.out.println(input.numberOfFlashes);
    }

    @Test
    public void t() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day11_Test2.txt").toURI());
        SmallInput input = new SmallInput(i);
        for (int it = 0; it <= 2; it++) {
            input.step();
        }

        System.out.println(input.numberOfFlashes);
    }

    @Test
    public void day11_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day11.txt").toURI());
        Input input = new Input(i);
        int step = 0;
        for (int it = 0; it < Integer.MAX_VALUE; it++) {
            input.step();
            if (input.allFlashed()) {
                step = it + 1;
                break;
            }
        }
        System.out.println(step);
    }

    private class Input {

        int[][] grid;
        long numberOfFlashes;

        public Input(List<String> s) {
            grid = new int[10][10];
            for (int y = 0; y < 10; y++) {
                char[] row = s.get(y).toCharArray();
                for (int x = 0; x < 10; x++) {
                    grid[y][x] = Integer.parseInt(Character.toString(row[x]));
                }
            }
        }

        public void step() {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    grid[y][x]++;
                }
            }
            List<Coord> maxLevelOctopuses = flashMaxLevelOctopuses();
            maxLevelOctopuses.forEach(this::processNeighbors);
        }

        private void processNeighbors(Coord coord) {
            if (coord.y > 0) {
                if (grid[coord.y - 1][coord.x] != 0) grid[coord.y - 1][coord.x]++;
                if (coord.x > 0) {
                    if (grid[coord.y - 1][coord.x - 1] != 0) grid[coord.y - 1][coord.x - 1]++;
                }
                if (coord.x < 9) {
                    if (grid[coord.y - 1][coord.x + 1] != 0) grid[coord.y - 1][coord.x + 1]++;
                }
            }
            if (coord.y < 9) {
                if (grid[coord.y + 1][coord.x] != 0) grid[coord.y + 1][coord.x]++;
                if (coord.x > 0) {
                    if (grid[coord.y + 1][coord.x - 1] != 0) grid[coord.y + 1][coord.x - 1]++;
                }
                if (coord.x < 9) {
                    if (grid[coord.y + 1][coord.x + 1] != 0) grid[coord.y + 1][coord.x + 1]++;
                }
            }
            if (coord.x > 0) {
                if (grid[coord.y][coord.x - 1] != 0) grid[coord.y][coord.x - 1]++;
            }
            if (coord.x < 9) {
                if (grid[coord.y][coord.x + 1] != 0) grid[coord.y][coord.x + 1]++;
            }
            List<Coord> maxLevelOctopuses = flashMaxLevelOctopuses();
            maxLevelOctopuses.forEach(this::processNeighbors);
        }

        private List<Coord> flashMaxLevelOctopuses() {
            List<Coord> maxLevel = new ArrayList<>();
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    if (grid[y][x] > 9) {
                        maxLevel.add(new Coord(x, y));
                        grid[y][x] = 0;
                        numberOfFlashes++;
                    }
                }
            }
            return maxLevel;
        }

        public boolean allFlashed() {
            boolean allFlashed = true;
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    if (grid[y][x] != 0) {
                        allFlashed = false;
                        break;
                    }
                }
            }
            return allFlashed;
        }

    }

    private class SmallInput {

        int[][] grid;
        long numberOfFlashes;

        public SmallInput(List<String> s) {
            grid = new int[5][5];
            for (int y = 0; y < 5; y++) {
                char[] row = s.get(y).toCharArray();
                for (int x = 0; x < 5; x++) {
                    grid[y][x] = Integer.parseInt(Character.toString(row[x]));
                }
            }
        }

        public void step() {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    grid[y][x]++;
                }
            }
            List<Coord> maxLevelOctopuses = flashMaxLevelOctopuses();
            maxLevelOctopuses.forEach(this::processNeighbors);
        }

        private void processNeighbors(Coord coord) {
            if (coord.y > 0) {
                if (grid[coord.y - 1][coord.x] != 0) grid[coord.y - 1][coord.x]++;
                if (coord.x > 0) {
                    if (grid[coord.y - 1][coord.x - 1] != 0) grid[coord.y - 1][coord.x - 1]++;
                }
                if (coord.x < 9) {
                    if (grid[coord.y - 1][coord.x + 1] != 0) grid[coord.y - 1][coord.x + 1]++;
                }
            }
            if (coord.y < 9) {
                if (grid[coord.y + 1][coord.x] != 0) grid[coord.y + 1][coord.x]++;
                if (coord.x > 0) {
                    if (grid[coord.y + 1][coord.x - 1] != 0) grid[coord.y + 1][coord.x - 1]++;
                }
                if (coord.x < 9) {
                    if (grid[coord.y + 1][coord.x + 1] != 0) grid[coord.y + 1][coord.x + 1]++;
                }
            }
            if (coord.x > 0) {
                if (grid[coord.y][coord.x - 1] != 0) grid[coord.y][coord.x - 1]++;
            }
            if (coord.x < 9) {
                if (grid[coord.y][coord.x + 1] != 0) grid[coord.y][coord.x + 1]++;
            }
            List<Coord> maxLevelOctopuses = flashMaxLevelOctopuses();
            maxLevelOctopuses.forEach(this::processNeighbors);
        }

        private List<Coord> flashMaxLevelOctopuses() {
            List<Coord> maxLevel = new ArrayList<>();
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    if (grid[y][x] > 9) {
                        maxLevel.add(new Coord(x, y));
                        grid[y][x] = 0;
                        numberOfFlashes++;
                    }
                }
            }
            return maxLevel;
        }
    }


    private class Coord {
        private int x;
        private int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
