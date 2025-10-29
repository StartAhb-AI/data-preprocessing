package org.example;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class ReadCSVExample {
    public static void main(String[] args) {
        List<String[]> records = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data.csv"))) {
            records = reader.readAll();  // Reads all rows
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print the CSV content
        for (String[] row : records) {
            System.out.println(Arrays.toString(row));
        }
    }
}
