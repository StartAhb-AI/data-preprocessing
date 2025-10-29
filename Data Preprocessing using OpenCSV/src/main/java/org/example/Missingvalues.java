package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class Missingvalues {
    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/data.csv";

        // Read CSV
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<String[]> records = reader.readAll();
        reader.close();

        // Separate header
        String[] header = records.get(0);
        records.remove(0);

        // Collect numeric values
        List<Double> ages = new ArrayList<>();
        List<Double> salaries = new ArrayList<>();

        for (String[] row : records) {
            if (!row[1].isEmpty()) ages.add(Double.parseDouble(row[1]));
            if (!row[2].isEmpty()) salaries.add(Double.parseDouble(row[2]));
        }

        // Compute mean and median
        double meanAge = ages.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double meanSalary = salaries.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        double medianAge = getMedian(ages);
        double medianSalary = getMedian(salaries);

        // Replace missing values (using mean here, you can switch to median if you want)
        for (String[] row : records) {
            if (row[1].isEmpty()) row[1] = String.valueOf(meanAge);     // Replace missing Age
            if (row[2].isEmpty()) row[2] = String.valueOf(medianSalary); // Replace missing Salary
        }

        // Print updated dataset
        System.out.println(Arrays.toString(header));
        for (String[] row : records) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Helper: Median Calculation
    private static double getMedian(List<Double> list) {
        Collections.sort(list);
        int n = list.size();
        if (n % 2 == 0) {
            return (list.get(n / 2 - 1) + list.get(n / 2)) / 2.0;
        } else {
            return list.get(n / 2);
        }
    }
}
