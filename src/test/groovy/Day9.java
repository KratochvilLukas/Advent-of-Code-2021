import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {

    @Test
    public void day9_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day9.txt").toURI());
        Input input = new Input(i);
        int sumRiskLevels = input.resultCoords.stream().map(Coord::getRiskLevel).mapToInt(risk -> risk).sum();
        System.out.println(sumRiskLevels);


    }

    @Test
    public void day9_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day9.txt").toURI());
        Input input = new Input(i);
        List<Integer> collect = input.resultCoords.stream().map(coord -> coord.sizeOfBasin).sorted().collect(Collectors.toList());
        long result = (long) collect.get(collect.size() - 1) * collect.get(collect.size() - 2) * collect.get(collect.size() - 3);
        System.out.println(result);
    }

    private class Input {
        public int[][] map;
        public List<Coord> resultCoords;

        public Input(List<String> input) {
            int xLength = input.get(0).length();
            map = new int[input.size()][xLength];
            for (int y = 0; y < input.size(); y++) {
                String row = input.get(y);
                for (int x = 0; x < xLength; x++) {
                    map[y][x] = Integer.parseInt(Character.toString(row.charAt(x)));
                }
            }
            resultCoords = getMinCoords(input.size(), xLength);
        }

        private List<Coord> getMinCoords(int yLength, int xLength) {
            List<Coord> result = new ArrayList<>();
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    boolean min = true;
                    int number = map[y][x];
                    if (y > 0) {
                        min = number < map[y - 1][x];
                    }
                    if (y < yLength - 1) {
                        min = min && number < map[y + 1][x];
                    }
                    if (x > 0) {
                        min = min && number < map[y][x - 1];
                    }
                    if (x < xLength - 1) {
                        min = min && number < map[y][x + 1];
                    }
                    if (min) {
                        int sizeOfBasin = getPartsOfBasin(x, y, number, yLength, xLength).size() + 1;
                        result.add(new Coord(x, y, number, sizeOfBasin));
                    }
                }
            }
            return result;
        }

        private Set<CoordSimple> getPartsOfBasin(int xMin, int yMin, int number, int maxY, int maxX) {
            Set<CoordSimple> partsOfBasin = new HashSet<>();
            int compareNumber;
            //VERTICAL
            if (yMin > 0) {
                compareNumber = map[yMin - 1][xMin];
                if (compareNumber != 9 && compareNumber > number) {
                    partsOfBasin.add(new CoordSimple(xMin, yMin - 1));
                    partsOfBasin.addAll(getPartsOfBasin(xMin, yMin - 1, compareNumber, maxY, maxX));
                }
            }
            if (yMin < maxY - 1) {
                compareNumber = map[yMin + 1][xMin];
                if (compareNumber != 9 && compareNumber > number) {
                    partsOfBasin.add(new CoordSimple(xMin, yMin + 1));
                    partsOfBasin.addAll(getPartsOfBasin(xMin, yMin + 1, compareNumber, maxY, maxX));
                }
            }
            //HORIZONTAL
            if (xMin > 0) {
                compareNumber = map[yMin][xMin - 1];
                if (compareNumber != 9 && compareNumber > number) {
                    partsOfBasin.add(new CoordSimple(xMin - 1, yMin));
                    partsOfBasin.addAll(getPartsOfBasin(xMin - 1, yMin, compareNumber, maxY, maxX));
                }
            }
            if (xMin < maxX - 1) {
                compareNumber = map[yMin][xMin + 1];
                if (compareNumber != 9 && compareNumber > number) {
                    partsOfBasin.add(new CoordSimple(xMin + 1, yMin));
                    partsOfBasin.addAll(getPartsOfBasin(xMin + 1, yMin, compareNumber, maxY, maxX));
                }
            }
//            //DIAGONAL
//            if (yMin > 0 && xMin > 0) {
//                compareNumber = map[yMin - 1][xMin - 1];
//                if (compareNumber != 9 && compareNumber > number) {
//                    partsOfBasin.add(new CoordSimple(xMin - 1, yMin - 1));
//                    partsOfBasin.addAll(getPartsOfBasin(xMin - 1, yMin - 1, compareNumber, maxY, maxX));
//                }
//            }
//            if (yMin > 0 && xMin < maxX - 1) {
//                compareNumber = map[yMin - 1][xMin + 1];
//                if (compareNumber != 9 && compareNumber > number) {
//                    partsOfBasin.add(new CoordSimple(xMin + 1, yMin - 1));
//                    partsOfBasin.addAll(getPartsOfBasin(xMin + 1, yMin - 1, compareNumber, maxY, maxX));
//                }
//            }
//            if (yMin < maxY - 1 && xMin > 0) {
//                compareNumber = map[yMin + 1][xMin - 1];
//                if (compareNumber != 9 && compareNumber > number) {
//                    partsOfBasin.add(new CoordSimple(xMin - 1, yMin + 1));
//                    partsOfBasin.addAll(getPartsOfBasin(xMin - 1, yMin + 1, compareNumber, maxY, maxX));
//                }
//            }
//            if (yMin < maxY - 1 && xMin < maxX - 1) {
//                compareNumber = map[yMin + 1][xMin + 1];
//                if (compareNumber != 9 && compareNumber > number) {
//                    partsOfBasin.add(new CoordSimple(xMin + 1, yMin + 1));
//                    partsOfBasin.addAll(getPartsOfBasin(xMin + 1, yMin + 1, compareNumber, maxY, maxX));
//                }
//            }

            return partsOfBasin;
        }
    }

    private class Coord {
        int x;
        int y;
        int number;
        int sizeOfBasin;

        public Coord(int x, int y, int number, int sizeOfBasin) {
            this.x = x;
            this.y = y;
            this.number = number;
            this.sizeOfBasin = sizeOfBasin;
        }

        public int getRiskLevel() {
            return this.number + 1;
        }
    }

    private class CoordSimple {
        int x;
        int y;

        public CoordSimple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CoordSimple that = (CoordSimple) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
