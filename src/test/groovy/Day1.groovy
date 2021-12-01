import org.junit.jupiter.api.Test

import java.util.stream.Collectors

class Day1 {

    @Test
    void 'day 1 - 1'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day1.txt").toURI())
        def inputIntegers = input.stream()
                .map {Integer.valueOf(it)}.collect(Collectors.toList())
        int increaseCount = 0
        for (int i = 1; i <= inputIntegers.size(); i++) {
            if (inputIntegers[i] > inputIntegers[i-1]) {
                increaseCount++
            }
        }
        println increaseCount
    }

    @Test
    void 'day 1 - 2'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day1.txt").toURI())
        def ints = input.stream()
                .map {Integer.valueOf(it)}.collect(Collectors.toList())
        int increaseCount = 0
        int previousSum = 0
        for (int i = 2; i < ints.size(); i++) {
            def sum = ints[i] + ints[i-1] + ints[i-2]
            if (previousSum != 0) {
                if (sum > previousSum) {
                    increaseCount++
                }
            }
            previousSum = sum
        }
        println increaseCount
    }

}
