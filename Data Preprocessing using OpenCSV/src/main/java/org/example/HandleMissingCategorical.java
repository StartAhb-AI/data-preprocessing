package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class HandleMissingCategorical {
    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/data.csv";

        // Read CSV
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<String[]> records = reader.readAll();
        reader.close();

        // Separate header
        String[] header = records.get(0);
        records.remove(0);

        // Collect Gender values
        Map<String, Integer> freq = new HashMap<>();
        for (String[] row : records) {
            if (!row[3].isEmpty() && !row[3].equals("?")) {
                freq.put(row[3], freq.getOrDefault(row[3], 0) + 1);
            }
        }

        // Find most frequent gender (mode)
        String modeGender = Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Replace missing values
        for (String[] row : records) {
            if (row[3].isEmpty() || row[3].equals("?")) {
                // Option 1: Replace with mode
                row[3] = modeGender;

                // Option 2: Uncomment this to replace with constant value
                // row[3] = "Unknown";
            }
        }

        // Print updated dataset
        System.out.println(Arrays.toString(header));
        for (String[] row : records) {
            System.out.println(Arrays.toString(row));
        }
    }
}
