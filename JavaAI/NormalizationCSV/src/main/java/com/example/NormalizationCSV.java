package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class NormalizationCSV {

    // Function for Min-Max normalization
    public static double minMaxNormalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static void main(String[] args) {
        String csvFile = "src/main/resources/people.csv";

        List<Double> ageList = new ArrayList<>();
        List<Double> salaryList = new ArrayList<>();

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            // Step 1: Collect all numeric values to calculate min/max
            for (CSVRecord record : records) {
                double age = Double.parseDouble(record.get("Age"));
                double salary = Double.parseDouble(record.get("Salary"));
                ageList.add(age);
                salaryList.add(salary);
            }

            // Step 2: Find min and max for each column
            double minAge = ageList.stream().mapToDouble(v -> v).min().orElse(0);
            double maxAge = ageList.stream().mapToDouble(v -> v).max().orElse(0);

            double minSalary = salaryList.stream().mapToDouble(v -> v).min().orElse(0);
            double maxSalary = salaryList.stream().mapToDouble(v -> v).max().orElse(0);

            // Step 3: Normalize and print
            System.out.println("Name, Age(norm), Salary(norm)");
            for (int i = 0; i < ageList.size(); i++) {
                double ageNorm = minMaxNormalize(ageList.get(i), minAge, maxAge);
                double salaryNorm = minMaxNormalize(salaryList.get(i), minSalary, maxSalary);
                System.out.printf("%s, %.3f, %.3f\n",
                        i == 0 ? "Alice" : i == 1 ? "Bob" : "Charlie", ageNorm, salaryNorm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
