import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day10 {

    @Test
    public void day10_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day10.txt").toURI());
        int sum = i.stream().map(Input::new)
                .mapToInt(Input::getInvalidValue)
                .sum();
        System.out.println(sum);
    }

    @Test
    public void day10_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day10.txt").toURI());
        List<Long> sum = i.stream().map(Input::new)
                .filter(in -> in.completeSum > 0)
                .map(in -> in.completeSum)
                .sorted()
                .collect(Collectors.toList());
        int middle = (sum.size() - 1)/2;
        System.out.println(sum.get(middle));
    }

    private class Input {
        List<Character> expected = new LinkedList<>();
        Character invalid;
        long completeSum;

        private Input(String s) {
            char[] array = s.toCharArray();
            for (int i = 0; i < array.length; i++) {
                if (isClosing(array[i])) {
                    if (expected.get(expected.size() - 1) == array[i]) {
                        expected.remove(expected.size() - 1);
                    } else {
                        invalid = array[i];
                        break;
                    }
                } else {
                    expected.add(getClosing(array[i]));
                }
            }
            completeSum = complete();
        }

        private long complete() {
            long sum = 0;
            if (invalid != null) {
                return sum;
            }
            for (int i = expected.size() - 1; i >=0; i--) {
                sum = sum * 5;
                sum += getValue(expected.get(i));
            }
            return sum;
        }

        private long getValue(Character character) {
            switch (character) {
                case ')':
                    return 1;
                case '>':
                    return 4;
                case '}':
                    return 3;
                case ']':
                    return 2;
                default:
                    return 0;
            }
        }

        private Character getClosing(char c) {
            switch (c) {
                case '(':
                    return ')';
                case '<':
                    return '>';
                case '{':
                    return '}';
                case '[':
                    return ']';
                default:
                    return null;
            }
        }

        private int getInvalidValue() {
            if (invalid == null) {
                return 0;
            }
            switch (invalid) {
                case ')':
                    return 3;
                case '>':
                    return 25137;
                case '}':
                    return 1197;
                case ']':
                    return 57;
                default:
                    return 0;
            }
        }

        public Character getInvalid() {
            return invalid;
        }

        private boolean isClosing(char ch) {
            return ch == ')' ||
                    ch == ']' ||
                    ch == '>' ||
                    ch == '}';
        }

    }
}
