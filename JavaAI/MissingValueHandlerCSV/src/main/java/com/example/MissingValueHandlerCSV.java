package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public class MissingValueHandlerCSV {

    // Function to handle missing string values
    public static String handleMissing(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;  // replace with default
        }
        return value;
    }

    public static void main(String[] args) {
        String csvFile = "src/main/resources/people.csv";

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String name = handleMissing(record.get("Name"), "Unknown");
                String age = handleMissing(record.get("Age"), "0");   // Fill missing Age with 0
                String city = handleMissing(record.get("City"), "Unknown");

                System.out.println(name + ", " + age + ", " + city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
