import org.junit.jupiter.api.Test

import java.util.stream.Collectors

class Day2 {

    @Test
    void 'day 2 - 1'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day2.txt").toURI())
        int horizontal = 0
        int depth = 0
        def inputs = input.stream()
                .map { new Input(it)  }.collect(Collectors.toList())
        inputs.forEach{
            if (it.direction == "forward") {
                horizontal += it.number
            } else if (it.direction == "up") {
                depth -= it.number
            } else {
                depth += it.number
            }
        }
        println horizontal * depth
    }

    @Test
    void 'day 2 - 2'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day2.txt").toURI())
        int horizontal = 0
        int depth = 0
        int aim = 0
        def inputs = input.stream()
                .map { new Input(it)  }.collect(Collectors.toList())
        inputs.forEach{
            if (it.direction == "forward") {
                horizontal += it`.number
                depth += it.number * aim
            } else if (it.direction == "up") {
                aim -= it.number
            } else {
                aim += it.number
            }
        }
        println horizontal * depth
    }

    private class Input {
        String direction
        int number

        Input(String input) {
            def splitInput = input.split(" ")
            direction = splitInput[0]
            number = Integer.valueOf(splitInput[1])
        }

    }
}
