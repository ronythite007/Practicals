import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple standalone Java program to find the hottest and coolest year from CSV weather data.
 *
 * CSV format: year,temperature
 * Example: 2015,32.5
 */
public class WeatherAnalysis {

    public static void main(String[] args) throws Exception {
        // Use WeatherData.csv by default, or take a file path from the command line.
        String fileName = args.length > 0 ? args[0] : "WeatherData.csv";

        // Store total temperature and count for each year.
        Map<Integer, double[]> stats = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Skip the header row.
                if (line.toLowerCase().startsWith("year")) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 2) {
                    continue;
                }

                int year = Integer.parseInt(parts[0].trim());
                double temperature = Double.parseDouble(parts[1].trim());

                stats.putIfAbsent(year, new double[2]);
                stats.get(year)[0] += temperature; // sum
                stats.get(year)[1] += 1;           // count
            }
        }

        int hottestYear = -1;
        int coolestYear = -1;
        double hottestAvg = Double.NEGATIVE_INFINITY;
        double coolestAvg = Double.POSITIVE_INFINITY;

        System.out.println("Average temperature per year:");
        for (Map.Entry<Integer, double[]> entry : stats.entrySet()) {
            int year = entry.getKey();
            double sum = entry.getValue()[0];
            double count = entry.getValue()[1];
            double average = sum / count;

            System.out.println(year + " -> " + String.format("%.2f", average));

            if (average > hottestAvg) {
                hottestAvg = average;
                hottestYear = year;
            }
            if (average < coolestAvg) {
                coolestAvg = average;
                coolestYear = year;
            }
        }

        System.out.println();
        System.out.println("Hottest year: " + hottestYear + " (" + String.format("%.2f", hottestAvg) + ")");
        System.out.println("Coolest year: " + coolestYear + " (" + String.format("%.2f", coolestAvg) + ")");
    }
}
