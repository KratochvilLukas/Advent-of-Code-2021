import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

    @Test
    public void day8_1() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day8.txt").toURI());
        List<Input> inputs = input.stream().map(Input::new).collect(Collectors.toList());

        AtomicLong count1478 = new AtomicLong();
        inputs.forEach(i -> {
            count1478.addAndGet(i.getCountInOutput(2));
            count1478.addAndGet(i.getCountInOutput(4));
            count1478.addAndGet(i.getCountInOutput(3));
            count1478.addAndGet(i.getCountInOutput(7));
        });

        System.out.println(count1478.get());

    }

    @Test
    public void day8_2() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day8.txt").toURI());
        List<Input> inputs = input.stream().map(Input::new).collect(Collectors.toList());
        long sum = inputs.stream().mapToLong(Input::detectOutput).sum();
        System.out.println(sum);
    }

    private String getSortedInput(String inputString) {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private class Input {
        private List<String> input;
        private List<String> output;
        //5 segmentu -> 2, 3, 5
        //6 segmentu -> 0, 6, 9
        private String code1;
        private String code4;
        private String code7;
        private String code8;
        private String code3;
        private String code2;
        private String code5;
        private String code0;
        private String code6;
        private String code9;

        public Input(String input) {
            String[] split = input.split(" \\| ");
            this.input = Arrays.stream(split[0].split(" ")).map(Day8.this::getSortedInput).collect(Collectors.toList());
            this.output = Arrays.stream(split[1].split(" ")).map(Day8.this::getSortedInput).collect(Collectors.toList());
            this.code1 = getCodeForSegments(2);
            this.code4 = getCodeForSegments(4);
            this.code7 = getCodeForSegments(3);
            this.code8 = getCodeForSegments(7);
            List<String> segments5 = getCodesWithSegments(5);
            this.code3 = detect3(segments5);
            this.code5 = detect5(segments5);
            this.code2 = detect2(segments5);
            List<String> segments6 = getCodesWithSegments(6);
            this.code9 = detect9(segments6);
            this.code6 = detect6(segments6);
            this.code0 = detect0(segments6);
        }

        private String detect0(List<String> segments6) {
            return segments6.stream()
                    .filter(i -> !i.equals(code9) && !i.equals(code6))
                    .findFirst().orElse(null);
        }

        private String detect6(List<String> segments6) {
            return segments6.stream()
                    .filter(i -> !i.equals(code9))
                    .filter(i -> containsAll(i, code5))
                    .findFirst().orElse(null);
        }

        private String detect9(List<String> segments6) {
            return segments6.stream().filter(input -> this.containsAll(input, code4)).findFirst().orElse(null);
        }

        private String detect5(List<String> segments5) {
            return segments5.stream()
                    .filter(i -> !i.equals(code3))
                    .filter(i -> getDiff(code4, i) == 1)
                    .findFirst().orElse(null);
        }

        private String detect2(List<String> segments5) {
            return segments5.stream()
                    .filter(i -> !i.equals(code3) && !i.equals(code5))
                    .findFirst().orElse(null);
        }

        private String detect3(List<String> segments5) {
            return segments5.stream().filter(input -> this.containsAll(input, code7)).findFirst().orElse(null);
        }

        private boolean containsAll(String input, String expected) {
            return convertStringToCharList(input).containsAll(convertStringToCharList(expected));
        }

        private List<Character> convertStringToCharList(String str) {
            List<Character> chars = new ArrayList<>();
            for (char ch : str.toCharArray()) {
                chars.add(ch);
            }
            return chars;
        }

        private int getDiff(String small, String big) {
            return Sets.difference(new HashSet<>(convertStringToCharList(small)), new HashSet<>(convertStringToCharList(big))).size();
        }

        private long getCountInOutput(int segments) {
            return output.stream().filter(out -> out.length() == segments).count();
        }

        private String getCodeForSegments(int segments) {
            String code = null;
            code = input.stream().filter(i -> i.length() == segments).findFirst()
                    .orElseGet(() ->  input.stream().filter(i -> i.length() == segments).findFirst().orElse(null));
            return code;
        }

        private List<String> getCodesWithSegments(int segments) {
            return input.stream().filter(i -> i.length() == segments).collect(Collectors.toList());
        }

        public long detectOutput() {
            return Long.parseLong(this.output.stream().map(this::getNumberForCode).collect(Collectors.joining("")));
        }

        private String getNumberForCode(String code) {
            if (code.equals(code0)) {
                return "0";
            }
            if (code.equals(code1)) {
                return "1";
            }
            if (code.equals(code2)) {
                return "2";
            }
            if (code.equals(code3)) {
                return "3";
            }
            if (code.equals(code4)) {
                return "4";
            }
            if (code.equals(code5)) {
                return "5";
            }
            if (code.equals(code6)) {
                return "6";
            }
            if (code.equals(code7)) {
                return "7";
            }
            if (code.equals(code8)) {
                return "8";
            }
            if (code.equals(code9)) {
                return "9";
            }
            return null;

        }
    }
}
