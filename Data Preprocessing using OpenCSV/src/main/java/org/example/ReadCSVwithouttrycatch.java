package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ReadCSVwithouttrycatch {
    public static void main(String[] args) throws Exception {
        // Path to your CSV file
        String filePath = "src/main/resources/data.csv";


        // Create CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath));

        // Read all rows
        List<String[]> rows = reader.readAll();

        // Print rows
        for (String[] row : rows) {
            System.out.println(Arrays.toString(row));
        }

        reader.close();
    }
}
