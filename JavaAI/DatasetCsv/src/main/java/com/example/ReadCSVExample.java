package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVExample {
    public static void main(String[] args) {
        String csvFile = "src/main/resources/people.csv";
        List<String[]> data = new ArrayList<>();

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String age = record.get("Age");
                String city = record.get("City");
                data.add(new String[]{name, age, city});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data.forEach(row -> System.out.println(String.join(", ", row)));
    }
}
