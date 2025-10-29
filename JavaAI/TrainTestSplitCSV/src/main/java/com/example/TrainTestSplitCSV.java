package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainTestSplitCSV {
    public static void main(String[] args) {
        String csvFile = "src/main/resources/people.csv";

        List<CSVRecord> dataset = new ArrayList<>();

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                dataset.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Shuffle data (important for random split)
        Collections.shuffle(dataset);

        // Split ratio 70% train / 30% test
        int trainSize = (int) (dataset.size() * 0.7);

        List<CSVRecord> trainSet = dataset.subList(0, trainSize);
        List<CSVRecord> testSet = dataset.subList(trainSize, dataset.size());

        // Print train set
        System.out.println("----- TRAINING SET -----");
        for (CSVRecord row : trainSet) {
            System.out.printf("%s, %s, %s, %s\n",
                    row.get("Name"), row.get("Age"), row.get("Gender"), row.get("City"));
        }

        // Print test set
        System.out.println("\n----- TEST SET -----");
        for (CSVRecord row : testSet) {
            System.out.printf("%s, %s, %s, %s\n",
                    row.get("Name"), row.get("Age"), row.get("Gender"), row.get("City"));
        }
    }
}
