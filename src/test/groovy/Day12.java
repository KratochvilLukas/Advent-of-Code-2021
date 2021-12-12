import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {

    @Test
    public void day12_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day12.txt").toURI());
        Input input = new Input(i);
        input.startToEndRoad.walk();

        System.out.println(input.startToEndRoad.possibleWays);
    }

    @Test
    public void day12_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day12.txt").toURI());
        Input input = new Input(i);
        input.startToEndRoad.walkImproved();

        System.out.println(input.startToEndRoad.possibleWays);

    }

    private class Input {

        private Map<String, Cave> caves = new HashMap<>();
        private Road startToEndRoad;

        public Input(List<String> s) {
            s.forEach(i -> {
                String[] path = i.split("-");
                caves.putIfAbsent(path[0], new Cave(path[0]));
                caves.putIfAbsent(path[1], new Cave(path[1]));
                Cave cave1 = caves.get(path[0]);
                Cave cave2 = caves.get(path[1]);
                cave1.neighbors.add(cave2);
                cave2.neighbors.add(cave1);
            });
            caves.values().forEach(v -> v.neighbors.sort(Cave::compareTo));
            Cave start = caves.get("start");
            Cave end = caves.get("end");
            startToEndRoad = new Road(start, end);
        }
    }

    private class Cave implements Comparable {
        private String name;
        private boolean big;
        private List<Cave> neighbors;

        public Cave(String name) {
            this.name = name;
            this.big = name.equals(name.toUpperCase());
            this.neighbors = new ArrayList<>();
        }



        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cave cave = (Cave) o;
            return Objects.equals(name, cave.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "Cave{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if (this.name.equals("end")) {
                return 1;
            }
            if (((Cave)o).name.equals("end")) {
                return -1;
            }
            if (this.name.equals("start")) {
                return -1;
            }
            if (((Cave)o).name.equals("start")) {
                return 1;
            }
            return this.name.compareTo(((Cave)o).name);
        }
    }

    private class Road {
        Cave start;
        Cave end;
        boolean reached;
        int possibleWays;
        List<Cave> allRoad;

        private Road(Cave start, Cave end) {
            this.start = start;
            this.end = end;
            this.allRoad = new ArrayList<>();
            this.allRoad.add(start);
        }

        private Road(Cave start, Cave end, List<Cave> allRoad) {
            this.start = start;
            this.end = end;
            this.allRoad = new ArrayList<>(allRoad);
            this.allRoad.add(start);
        }


        public void walk() {
            for (Cave neighbor : start.neighbors) {
                if (!neighbor.equals(end)) {
                    if (neighbor.big || notAlreadyVisited(neighbor)) {
                        Road neighborToEnd = new Road(neighbor, end, allRoad);
                        neighborToEnd.walk();
                        if (neighborToEnd.reached) {
                            this.possibleWays += neighborToEnd.possibleWays;
                            this.reached = true;
                        };
                    }
                } else {
                    possibleWays++;
                    allRoad.add(end);
                    System.out.println(allRoad);
                    allRoad.remove(allRoad.size() - 1);
                    allRoad.remove(allRoad.size() - 1);
                    reached = true;
                }
            }
        }


        public void walkImproved() {
            for (Cave neighbor : start.neighbors) {
                if (!neighbor.equals(end)) {
                    if (neighbor.big || notAlreadyVisitedOrNoSmallVisitedTwice(neighbor)) {
                        Road neighborToEnd = new Road(neighbor, end, allRoad);
                        neighborToEnd.walkImproved();
                        if (neighborToEnd.reached) {
                            this.possibleWays += neighborToEnd.possibleWays;
                            this.reached = true;
                        };
                    }
                } else {
                    possibleWays++;
                    allRoad.add(end);
                    System.out.println(allRoad);
                    allRoad.remove(allRoad.size() - 1);
                    allRoad.remove(allRoad.size() - 1);
                    reached = true;
                }
            }
        }

        private boolean notAlreadyVisitedOrNoSmallVisitedTwice(Cave neighbor) {
            boolean notAlreadyVisited = notAlreadyVisited(neighbor);
            if (notAlreadyVisited) {
                return true;
            }
            if (neighbor.name.equals("start") || neighbor.name.equals("end")) {
                return false;
            }
            Optional<Integer> max = allRoad.stream()
                    .filter(cave -> !cave.big)
                    .map(cave -> cave.name)
                    .collect(Collectors.groupingBy(caveName -> caveName))
                    .values().stream()
                    .map(List::size)
                    .max(Integer::compareTo);
            return max.map(n -> n <= 1).orElse(false);
        }

        private boolean notAlreadyVisited(Cave neighbor) {
            return allRoad.stream()
                    .filter(neighbor::equals)
                    .findFirst().isEmpty();
        }
    }
}
