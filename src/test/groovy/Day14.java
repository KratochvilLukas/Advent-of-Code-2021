import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    @Test
    public void day14_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day14.txt").toURI());
        Input input = new Input(i);

//        input.process10();
//
//        System.out.println(input.quantityMostSubtractQuantityLess());
    }

    @Test
    public void day14_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day14_Test.txt").toURI());
        Input input = new Input(i);

//        input.process40();
//
//        System.out.println(input.quantityMostSubtractQuantityLess());

    }

    private class Result {
        Map<Character, Long> charactersCount10;
        Map<Character, Long> charactersCount40;
        int iterations;
        List<Character> template10;


        public Result(String pair, Map<String, Character> pairsMap) {
            this.iterations = 10;
            template10 = Arrays.asList(pair.charAt(0), pair.charAt(1));
            for (int i = 0; i < 10; i++) {
                template10 = processTemplate(template10, pairsMap);
            }
            charactersCount10 = template10
                    .stream()
                    .collect(Collectors.groupingBy(chara -> chara, Collectors.counting()));
        }

        private Map<Character, Long> mergeMaps(Map<Character, Long> map1, Map<Character, Long> map2) {
            var ref = new Object() {
                Map<Character, Long> charactersCount = new HashMap<>(map1);
            };
            ref.charactersCount = Stream.concat(ref.charactersCount.entrySet().stream(), map2.entrySet().stream())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                Long::sum));
            return ref.charactersCount;
        }



        private List<Character> processTemplate(List<Character> template, Map<String, Character> pairs) {
            List<Character> newTemplate = new LinkedList<>();
            for (int i = 1; i < template.size(); i++) {
                Character first = template.get(i-1);
                Character second = template.get(i);
                String pair = String.valueOf(first) + second;
                Character result = pairs.get(pair);
                newTemplate.add(first);
                newTemplate.add(result);
            }
            newTemplate.add(template.get(template.size()-1));
            return newTemplate;
        }
    }

    private class Input {
        List<Character> template;
        Map<String, Character> pairs = new HashMap<>();
        Map<String, Result> resultsAfterIterations;
        Map<String, List<List<Character>>> cache;

        public Input(List<String> s) {
            resultsAfterIterations = new HashMap<>();
            cache = new HashMap<>();
            template = s.get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            for (int i = 2; i < s.size(); i++) {
                pairs.put(s.get(i).split(" -> ")[0], s.get(i).split(" -> ")[1].toCharArray()[0]);
            }
            resultsAfterIterations = pairs.keySet().stream()
                    .collect(Collectors.toMap(key -> key, key -> new Result(key, pairs)));
            List<Result> results = new ArrayList<>();
            for (int i = 1; i < template.size(); i++) {
                String pair = template.get(i-1).toString() + template.get(i);
                results.add(resultsAfterIterations.get(pair));
            }
//            results.forEach(result -> result.addIterations(resultsAfterIterations));

            var ref = new Object() {
                Map<Character, Long> charactersCount = new HashMap<>();
            };
            results.forEach(result -> {
                ref.charactersCount = Stream.concat(ref.charactersCount.entrySet().stream(), result.charactersCount10.entrySet().stream())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                Long::sum));
            });

            System.out.println(quantityMostSubtractQuantityLess(ref.charactersCount));
        }


        private long quantityMostSubtractQuantityLess(Map<Character, Long> result) {
            Long max = result.entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getValue)
                    .get();

            Long min = result.entrySet().stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getValue)
                    .get();

            return max - min;
        }
    }
}
