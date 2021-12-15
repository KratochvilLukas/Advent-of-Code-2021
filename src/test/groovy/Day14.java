import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day14 {

    //CHEATED
    @Test
    public void day14_1() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day14.txt").toURI());
        Input input = new Input(i);

        Map<String, Long> calcPairCounts = input.calcPairCounts(10);
        System.out.println(input.countLetters(calcPairCounts));
    }

    @Test
    public void day14_2() throws URISyntaxException {
        List<String> i = FileInputReader.readInputFromFile(this.getClass().getResource("/Day14.txt").toURI());
        Input input = new Input(i);

        Map<String, Long> calcPairCounts = input.calcPairCounts(40);
        System.out.println(input.countLetters(calcPairCounts));

    }

    private class Input {
        public String template;
        public Map<String, String> insertions = new HashMap<>();

        public Input(List<String> strings) {
            template = strings.get(0);

            insertions = new HashMap<>();
            for(int i = 2; i < strings.size(); i++)
            {
                String s = strings.get(i);
                String[] parts = s.split(" -> ");
                insertions.put(parts[0], parts[1]);
            }
        }

        public Map<String, Long> calcPairCounts(int steps)
        {
            Map<String, Long> pairCounts = new HashMap<>();
            for(int i = 0; i < template.length() - 1; i++)
            {
                String pair = template.substring(i, i + 2);
                pairCounts.put(pair, pairCounts.getOrDefault(pair, 0L) + 1);
            }

            for(int i = 0; i < steps; i++)
            {
                Map<String, Long> newPairCounts = new HashMap<>();
                for(String pair : pairCounts.keySet())
                {
                    long pairCount = pairCounts.get(pair);
                    String insert = insertions.get(pair);
                    String newPair1 = pair.charAt(0) + insert;
                    String newPair2 = insert + pair.charAt(1);
                    newPairCounts.put(newPair1, newPairCounts.getOrDefault(newPair1, 0L) + pairCount);
                    newPairCounts.put(newPair2, newPairCounts.getOrDefault(newPair2, 0L) + pairCount);
                }
                pairCounts = newPairCounts;
            }
            return pairCounts;
        }


        public long countLetters(Map<String, Long> pairCounts)
        {
            Map<Character, Long> counts = new HashMap<>();

            for(String s : pairCounts.keySet())
            {
                long sCount = pairCounts.get(s);

                char c0 = s.charAt(0);
                counts.put(c0, counts.getOrDefault(c0, 0L) + sCount);

                char c1 = s.charAt(1);
                counts.put(c1, counts.getOrDefault(c1, 0L) + sCount);
            }

            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;

            for(long l : counts.values())
            {
                if(min > l)
                    min = l;
                if(max < l)
                    max = l;
            }

            return (max / 2) - (min / 2);
        }
    }
}
