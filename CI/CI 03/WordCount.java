// WordCount.java

import java.io.*;
import java.util.*;

public class WordCount {

    public static void main(String[] args) throws Exception {

        // Read file
        Map<String, Integer> map = new HashMap<>();
        String filePath = args.length > 0 ? args[0] : "sample.txt";

        // MAP phase: read each word and emit it as a key with a count of 1.
        // If the word appears again, increase the count for that key.
        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                String w = sc.next().toLowerCase();
                map.put(w, map.getOrDefault(w, 0) + 1);
            }
        }

        // REDUCE phase: combine all emitted counts into the final word totals.
        // The map already holds the aggregated result, so we just print it.
        // Output
        for (String k : map.keySet())
            System.out.println(k + " -> " + map.get(k));
    }
}