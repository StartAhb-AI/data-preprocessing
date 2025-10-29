import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class HandleMissingTypes {

    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/data3.csv";

        // Read CSV
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> allRows = reader.readAll();
        reader.close();

        String[] header = allRows.get(0);
        List<String[]> dataRows = new ArrayList<>(allRows.subList(1, allRows.size()));

        // Helper: convert string to Double safely
        java.util.function.Function<String, Double> safeParse = (s) ->
                (s == null || s.equalsIgnoreCase("NaN") || s.isEmpty()) ? null : Double.parseDouble(s);

        // --------------------------
        // 1. Drop Rows with Missing Values
        // --------------------------
        List<String[]> dropRows = new ArrayList<>();
        for (String[] row : dataRows) {
            if (!row[1].equalsIgnoreCase("NaN") && !row[2].equalsIgnoreCase("NaN")) {
                dropRows.add(row);
            }
        }
        writeCSV("drop_rows.csv", header, dropRows);

        // --------------------------
        // 2. Drop Columns with Missing Values
        // --------------------------
        List<String> newHeader = new ArrayList<>();
        List<Integer> keepIndexes = new ArrayList<>();
        for (int i = 0; i < header.length; i++) {
            boolean allNaN = true;
            for (String[] row : dataRows) {
                if (!row[i].equalsIgnoreCase("NaN")) {
                    allNaN = false;
                    break;
                }
            }
            if (!allNaN) {
                newHeader.add(header[i]);
                keepIndexes.add(i);
            }
        }

        List<String[]> dropCols = new ArrayList<>();
        for (String[] row : dataRows) {
            List<String> newRow = new ArrayList<>();
            for (int idx : keepIndexes) {
                newRow.add(row[idx]);
            }
            dropCols.add(newRow.toArray(new String[0]));
        }
        writeCSV("drop_cols.csv", newHeader.toArray(new String[0]), dropCols);

        // --------------------------
        // 3. Fill with Mean (Age)
        // --------------------------
        double sumAge = 0;
        int countAge = 0;
        for (String[] row : dataRows) {
            Double age = safeParse.apply(row[1]);
            if (age != null) {
                sumAge += age;
                countAge++;
            }
        }
        double meanAge = sumAge / countAge;

        List<String[]> meanRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            if (newRow[1].equalsIgnoreCase("NaN")) {
                newRow[1] = String.format("%.2f", meanAge);
            }
            meanRows.add(newRow);
        }
        writeCSV("fill_mean.csv", header, meanRows);

        // --------------------------
        // 4. Fill with Median (Age)
        // --------------------------
        List<Double> ageValues = new ArrayList<>();
        for (String[] row : dataRows) {
            Double age = safeParse.apply(row[1]);
            if (age != null) ageValues.add(age);
        }
        Collections.sort(ageValues);
        double medianAge = ageValues.get(ageValues.size() / 2);

        List<String[]> medianRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            if (newRow[1].equalsIgnoreCase("NaN")) {
                newRow[1] = String.valueOf(medianAge);
            }
            medianRows.add(newRow);
        }
        writeCSV("fill_median.csv", header, medianRows);

        // --------------------------
        // 5. Fill with Mode (Salary)
        // --------------------------
        Map<String, Integer> freq = new HashMap<>();
        for (String[] row : dataRows) {
            if (!row[2].equalsIgnoreCase("NaN")) {
                freq.put(row[2], freq.getOrDefault(row[2], 0) + 1);
            }
        }
        String modeSalary = Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();

        List<String[]> modeRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            if (newRow[2].equalsIgnoreCase("NaN")) {
                newRow[2] = modeSalary;
            }
            modeRows.add(newRow);
        }
        writeCSV("fill_mode.csv", header, modeRows);

        // --------------------------
        // 6. Forward Fill
        // --------------------------
        String lastAge = null, lastSalary = null;
        List<String[]> forwardRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);

            if (!newRow[1].equalsIgnoreCase("NaN")) lastAge = newRow[1];
            else if (lastAge != null) newRow[1] = lastAge;

            if (!newRow[2].equalsIgnoreCase("NaN")) lastSalary = newRow[2];
            else if (lastSalary != null) newRow[2] = lastSalary;

            forwardRows.add(newRow);
        }
        writeCSV("forward_fill.csv", header, forwardRows);

        // --------------------------
        // 7. Backward Fill
        // --------------------------
        String nextAge, nextSalary;
        List<String[]> backwardRows = new ArrayList<>(dataRows);

        for (int i = backwardRows.size() - 1; i >= 0; i--) {
            String[] row = Arrays.copyOf(backwardRows.get(i), backwardRows.get(i).length);

            if (row[1].equalsIgnoreCase("NaN")) {
                nextAge = findNextValue(backwardRows, i, 1);
                if (nextAge != null) row[1] = nextAge;
            }
            if (row[2].equalsIgnoreCase("NaN")) {
                nextSalary = findNextValue(backwardRows, i, 2);
                if (nextSalary != null) row[2] = nextSalary;
            }
            backwardRows.set(i, row);
        }
        writeCSV("backward_fill.csv", header, backwardRows);

        // --------------------------
        // 8. Fill with Constant Value
        // --------------------------
        List<String[]> constantRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            if (newRow[2].equalsIgnoreCase("NaN")) {
                newRow[2] = "0"; // Fill Salary with 0
            }
            constantRows.add(newRow);
        }
        writeCSV("fill_constant.csv", header, constantRows);

        System.out.println("✅ All missing value handling techniques applied!");
    }

    // Helper function: write CSV
    private static void writeCSV(String filename, String[] header, List<String[]> rows) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(filename));
        writer.writeNext(header);
        writer.writeAll(rows);
        writer.close();
    }

    // Helper function: find next non-NaN value
    private static String findNextValue(List<String[]> rows, int index, int col) {
        for (int i = index + 1; i < rows.size(); i++) {
            if (!rows.get(i)[col].equalsIgnoreCase("NaN")) {
                return rows.get(i)[col];
            }
        }
        return null;
    }
}
