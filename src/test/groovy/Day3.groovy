import org.junit.jupiter.api.Test

import java.util.stream.Collectors

class Day3 {

    @Test
    void 'day 3 - 1'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day3.txt").toURI())
        def inputs = input.stream()
                .map { new Input(it)  }.collect(Collectors.toList())
        def gamma = new int[12]
        def epsylon = new int[12]
        def numberOf1 = 0
        def numberOf0 = 0
        inputs.forEach{

        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < inputs.size(); j++) {
                if (inputs[j].bits[0][i] == 0) {
                    numberOf0++
                } else {
                    numberOf1++
                }
            }
            if (numberOf0 > numberOf1) {
                gamma[i] = 0
                epsylon[i] = 1
            } else {
                gamma[i] = 1
                epsylon[i] = 0
            }
            numberOf1 = 0
            numberOf0 = 0
        }
        println getBinaryToNumber(gamma) * getBinaryToNumber(epsylon)
    }

    @Test
    void 'day 3 - 2'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day3.txt").toURI())
        def inputs = input.stream()
                .map { new Input(it)  }.collect(Collectors.toList())

        List<Input> oxygen = getResult(inputs, 0, 1)
        List<Input> co2 = getResult(inputs, 1, 0)
        println getBinaryToNumber(oxygen[0].bits[0]) * getBinaryToNumber(co2[0].bits[0])

    }

    static List<Input> getResult(List<Input> inputs, int comparatorMore0, int comparatorLess0) {
        def numberOf1 = 0
        def numberOf0 = 0
        List<Input> co2 = new ArrayList<Input>(inputs)
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < co2.size(); j++) {
                if (co2[j].bits[0][i] == 0) {
                    numberOf0++
                } else {
                    numberOf1++
                }
            }
            if (numberOf0 == numberOf1) {
                co2 = co2.stream().filter {it -> it.bits[0][i] == comparatorLess0}.collect(Collectors.toList())
            }
            else if (numberOf0 > numberOf1) {
                if (co2.size() > 1) {
                    co2 = co2.stream().filter {it -> it.bits[0][i] == comparatorMore0}.collect(Collectors.toList())
                }
            } else {
                if (co2.size() > 1) {
                    co2 = co2.stream().filter { it -> it.bits[0][i] == comparatorLess0 }.collect(Collectors.toList())
                }
            }
            if (co2.size() == 1) {
                return co2
            }
            numberOf0 = 0
            numberOf1 = 0
        }
        return co2
    }

    static Long getBinaryToNumber(List<Integer> input) {
        long result = 0
        int currentIndex = 0
        for (int i = input.size() - 1; i >= 0; i--) {
            result += input[i] * Math.pow(2, currentIndex)
            currentIndex++
        }
        return result
    }

    private class Input {
        List<Integer> bits;

        Input(String input) {
            bits = Arrays.stream(input.split())
                    .map{it -> it.getChars()}
                    .map{it -> getIntList(Arrays.asList(it) as List<Character>)}
                    .collect(Collectors.toList())
        }

        List<Integer> getIntList(List<Character> list) {
            list.stream().map{it -> Integer.valueOf(it.toString())}.collect(Collectors.toList())
        }
    }
}
