import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class NormalizationExample {
    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/data4.csv";
        String minmaxFile = "minmax.csv";
        String zscoreFile = "zscore.csv";
        String decimalFile = "decimal.csv";

        // 1. Read CSV
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> allRows = reader.readAll();
        reader.close();

        String[] header = allRows.get(0);
        List<String[]> dataRows = new ArrayList<>(allRows.subList(1, allRows.size()));

        // Convert Age and Salary to numeric lists
        List<Double> ages = new ArrayList<>();
        List<Double> salaries = new ArrayList<>();
        for (String[] row : dataRows) {
            ages.add(Double.parseDouble(row[1]));
            salaries.add(Double.parseDouble(row[2]));
        }

        // ---------- Min-Max Normalization ----------
        double minAge = Collections.min(ages), maxAge = Collections.max(ages);
        double minSalary = Collections.min(salaries), maxSalary = Collections.max(salaries);

        List<String[]> minMaxRows = new ArrayList<>();
        for (int i = 0; i < dataRows.size(); i++) {
            String[] row = dataRows.get(i);
            double ageNorm = (ages.get(i) - minAge) / (maxAge - minAge);
            double salNorm = (salaries.get(i) - minSalary) / (maxSalary - minSalary);
            minMaxRows.add(new String[]{row[0],
                    String.format("%.2f", ageNorm),
                    String.format("%.2f", salNorm)});
        }
        writeCSV(minmaxFile, new String[]{"ID", "Age_MinMax", "Salary_MinMax"}, minMaxRows);

        // ---------- Z-Score Normalization ----------
        double meanAge = ages.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanSalary = salaries.stream().mapToDouble(s -> s).average().orElse(0.0);
        double stdAge = Math.sqrt(ages.stream().mapToDouble(a -> Math.pow(a - meanAge, 2)).sum() / ages.size());
        double stdSalary = Math.sqrt(salaries.stream().mapToDouble(s -> Math.pow(s - meanSalary, 2)).sum() / salaries.size());

        List<String[]> zScoreRows = new ArrayList<>();
        for (int i = 0; i < dataRows.size(); i++) {
            String[] row = dataRows.get(i);
            double ageZ = (ages.get(i) - meanAge) / stdAge;
            double salZ = (salaries.get(i) - meanSalary) / stdSalary;
            zScoreRows.add(new String[]{row[0],
                    String.format("%.2f", ageZ),
                    String.format("%.2f", salZ)});
        }
        writeCSV(zscoreFile, new String[]{"ID", "Age_ZScore", "Salary_ZScore"}, zScoreRows);

        // ---------- Decimal Scaling ----------
        int jAge = String.valueOf((int) Math.max(Math.abs(minAge), Math.abs(maxAge))).length();
        int jSal = String.valueOf((int) Math.max(Math.abs(minSalary), Math.abs(maxSalary))).length();

        List<String[]> decimalRows = new ArrayList<>();
        for (int i = 0; i < dataRows.size(); i++) {
            String[] row = dataRows.get(i);
            double ageDS = ages.get(i) / Math.pow(10, jAge);
            double salDS = salaries.get(i) / Math.pow(10, jSal);
            decimalRows.add(new String[]{row[0],
                    String.format("%.2f", ageDS),
                    String.format("%.2f", salDS)});
        }
        writeCSV(decimalFile, new String[]{"ID", "Age_Decimal", "Salary_Decimal"}, decimalRows);

        System.out.println("✅ Normalization complete! Files created: " +
                minmaxFile + ", " + zscoreFile + ", " + decimalFile);
    }

    // Utility method to write CSV
    private static void writeCSV(String filename, String[] header, List<String[]> rows) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(filename));
        writer.writeNext(header);
        writer.writeAll(rows);
        writer.close();
    }
}
