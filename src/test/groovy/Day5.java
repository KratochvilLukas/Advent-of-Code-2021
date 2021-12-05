import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    @Test
    public void day5_1() throws URISyntaxException {

        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day5.txt").toURI());

        List<Line> lines = input.stream()
                .map(Line::new)
                .collect(Collectors.toList());
        List<Line> horOrVertLines = lines.stream()
                .filter(Line::horizontalOrVertical)
                .collect(Collectors.toList());



        int[][] diagram = new int[1000][1000];
        horOrVertLines.forEach(line -> line.writeTo(diagram));
        int numberOfDangerousAreas = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (diagram[x][y] >= 2) {
                    numberOfDangerousAreas++;
                }
            }
        }
        System.out.println(numberOfDangerousAreas);

    }

    @Test
    public void day5_2() throws URISyntaxException {

        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day5.txt").toURI());

        List<Line> lines = input.stream()
                .map(Line::new)
                .collect(Collectors.toList());
        List<Line> horOrVertLines = lines.stream()
                .filter(Line::horizontalOrVertical)
                .collect(Collectors.toList());
        List<Line> diagonalLines = lines.stream()
                .filter(Line::diagonal)
                .collect(Collectors.toList());


        List<Line> others = lines.stream()
                .filter(line -> !line.diagonal())
                .filter(line -> !line.horizontalOrVertical())
                .collect(Collectors.toList());

        int[][] diagram = new int[1000][1000];
        horOrVertLines.forEach(line -> line.writeTo(diagram));
        for (int i = 0; i < diagonalLines.size(); i++) {
            diagonalLines.get(i).writeDiagonalTo(diagram);
        }
        int numberOfDangerousAreas = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (diagram[x][y] >= 2) {
                    numberOfDangerousAreas++;
                }
            }
        }
        System.out.println(numberOfDangerousAreas);

    }

    @Test
    public void day5_2_Test() throws URISyntaxException {

        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day5_Test.txt").toURI());

        List<Line> lines = input.stream()
                .map(Line::new)
                .collect(Collectors.toList());
        List<Line> horOrVertLines = lines.stream()
                .filter(Line::horizontalOrVertical)
                .collect(Collectors.toList());
        List<Line> diagonalLines = lines.stream()
                .filter(Line::diagonal)
                .collect(Collectors.toList());


        List<Line> others = lines.stream()
                .filter(line -> !line.diagonal())
                .filter(line -> !line.horizontalOrVertical())
                .collect(Collectors.toList());

        int[][] diagram = new int[10][10];
        horOrVertLines.forEach(line -> line.writeTo(diagram));
        for (int i = 0; i < diagonalLines.size(); i++) {
            diagonalLines.get(i).writeDiagonalTo(diagram);
        }
        int numberOfDangerousAreas = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (diagram[x][y] >= 2) {
                    numberOfDangerousAreas++;
                }
            }
        }
        System.out.println(numberOfDangerousAreas);

    }

    private class Line {
        private Coords from;
        private Coords to;

        public Line(String input) {
            String[] coords = input.split(" -> ");
            this.from = new Coords(coords[0]);
            this.to = new Coords(coords[1]);
        }

        public boolean horizontalOrVertical() {
            return from.x == to.x || from.y == to.y;
        }



        public boolean diagonal() {
            int lengthX = Math.abs(from.x - to.x);
            int lengthY = Math.abs(from.y - to.y);

            return lengthX == lengthY;

        }

        public void writeTo(int[][] diagram) {
            int startX = Math.min(from.x, to.x);
            int startY = Math.min(from.y, to.y);
            for (int x = startX; x <= startX + Math.abs(from.x - to.x); x++) {
                for (int y = startY; y <= startY + Math.abs(from.y - to.y); y++) {
                    diagram[x][y] += 1;
                }
            }
        }

        public void writeDiagonalTo(int[][] diagram) {
            Coords minXCoord = from.x < to.x ? from : to;
            Coords targetCoord = from.x < to.x ? to : from;
            int startX = minXCoord.x;
            int startY = minXCoord.y;
            int endI = Math.abs(from.x - to.x);

            for (int i = 0; i <= endI; i++) {
                if (minXCoord.y < targetCoord.y) {
                    diagram[startX + i][startY + i] += 1;
                } else {
                    diagram[startX + i][startY - i] += 1;
                }
            }
        }
    }

    private class Coords {
        private int x;
        private int y;

        public Coords(String input) {
            String[] coords = input.split(",");
            this.x = Integer.parseInt(coords[0]);
            this.y = Integer.parseInt(coords[1]);
        }

        @Override
        public String toString() {
            return "Coord: [" +
                    x +
                    ", " + y +
                    ']';
        }
    }


}
