import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day7 {

    @Test
    public void day7_1() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day7.txt").toURI());
        List<Integer> horizontalPositions = Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        long sum = horizontalPositions.stream().mapToInt(Integer::valueOf).sum();
        long count = horizontalPositions.size();
        OptionalInt min = horizontalPositions.stream().mapToInt(Integer::valueOf).min();
        OptionalInt max = horizontalPositions.stream().mapToInt(Integer::valueOf).max();


        long minLength = Long.MAX_VALUE;

        for (int middle = min.getAsInt(); middle < max.getAsInt(); middle++) {
            minLength = Math.min(minLength, getResult(horizontalPositions, middle));
        }

        System.out.println(minLength);

    }

    @Test
    public void day7_2() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day7.txt").toURI());
        List<Integer> horizontalPositions = Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        OptionalInt min = horizontalPositions.stream().mapToInt(Integer::valueOf).min();
        OptionalInt max = horizontalPositions.stream().mapToInt(Integer::valueOf).max();


        long minLength = Long.MAX_VALUE;

        for (int middle = min.getAsInt(); middle < max.getAsInt(); middle++) {
            minLength = Math.min(minLength, getResultWithIncrement(horizontalPositions, middle));
        }

        System.out.println(minLength);

    }

    private long getResult(List<Integer> horizontalPositions, long potentialMiddle) {
        return horizontalPositions.stream().mapToLong(i -> Math.abs(i - potentialMiddle)).sum();
    }

    private long getResultWithIncrement(List<Integer> horizontalPositions, long potentialMiddle) {
        List<Long> differences = horizontalPositions.stream()
                .map(i -> Math.abs(i - potentialMiddle))
                .map(this::calculateFuelCost)
                .collect(Collectors.toList());
        return differences.stream().mapToLong(i -> i).sum();
    }

    private Long calculateFuelCost(Long length) {
        long result = 0L;
        for (int currentFuelCost = 1; currentFuelCost <= length; currentFuelCost++) {
            result += currentFuelCost;
        }
        return result;
    }
}
