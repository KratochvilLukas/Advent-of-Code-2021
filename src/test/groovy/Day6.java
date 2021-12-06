import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Day6 {

    @Test
    public void day6_1() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day6.txt").toURI());
        List<Integer> internalNumbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());
        long add;
        for (long i = 0; i < 80; i++) {
            add = internalNumbers.stream()
                    .filter(number -> number == 0)
                    .count();
            internalNumbers = internalNumbers.stream().map(internalNumber -> {
               if (internalNumber == 0) {
                   internalNumber+=6;
               } else {
                   internalNumber--;
               }
               return internalNumber;
            }).collect(Collectors.toList());
            for (long j = 0; j < add; j++) {
                internalNumbers.add(8);
            }
        }
        System.out.println(internalNumbers.size());
    }

    @Test
    public void day6_2() throws URISyntaxException {
        List<String> input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day6.txt").toURI());
        List<Integer> internalNumbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        Map<Integer, AtomicLong> map = getEmptyMap();
        Map<Integer, AtomicLong> finalMap = map;
        internalNumbers.forEach(number -> finalMap.get(number).incrementAndGet());

        for (long i = 0; i < 256; i++) {
            long c0 = map.get(0).get();
            long c1 = map.get(1).get();
            long c2 = map.get(2).get();
            long c3 = map.get(3).get();
            long c4 = map.get(4).get();
            long c5 = map.get(5).get();
            long c6 = map.get(6).get();
            long c7 = map.get(7).get();
            long c8 = map.get(8).get();
            map = getEmptyMap();
            map.get(8).addAndGet(c0);
            map.get(7).addAndGet(c8);
            map.get(6).addAndGet(c7 + c0);
            map.get(5).addAndGet(c6);
            map.get(4).addAndGet(c5);
            map.get(3).addAndGet(c4);
            map.get(2).addAndGet(c3);
            map.get(1).addAndGet(c2);
            map.get(0).addAndGet(c1);
        }
        AtomicReference<Long> result = new AtomicReference<>(0L);
        map.values().forEach(value -> result.updateAndGet(v -> v + value.get()));
        System.out.println("shit");
    }

    private Map<Integer, AtomicLong> getEmptyMap() {
        Map<Integer, AtomicLong> map = new HashMap<>();
        map.put(0, new AtomicLong(0));
        map.put(1, new AtomicLong(0));
        map.put(2, new AtomicLong(0));
        map.put(3, new AtomicLong(0));
        map.put(4, new AtomicLong(0));
        map.put(5, new AtomicLong(0));
        map.put(6, new AtomicLong(0));
        map.put(7, new AtomicLong(0));
        map.put(8, new AtomicLong(0));
        return map;
    }


}
