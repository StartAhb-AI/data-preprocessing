import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class EncodingTechniques {

    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/data1.csv";
        String labelEncodedFile = "label_encoded.csv";
        String ordinalEncodedFile = "ordinal_encoded.csv";
        String oneHotEncodedFile = "onehot_encoded.csv";
        String frequencyEncodedFile = "frequency_encoded.csv";

        // 1️⃣ Read CSV
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> allRows = reader.readAll();
        reader.close();

        String[] header = allRows.get(0);
        List<String[]> dataRows = new ArrayList<>(allRows.subList(1, allRows.size()));

        // -------------------------
        // 2️⃣ Label Encoding Example (Color)
        // -------------------------
        int colorIndex = Arrays.asList(header).indexOf("Color");
        Map<String, Integer> colorLabelMap = new HashMap<>();
        int label = 0;
        for (String[] row : dataRows) {
            String value = row[colorIndex];
            if (!colorLabelMap.containsKey(value)) {
                colorLabelMap.put(value, label++);
            }
            row[colorIndex] = String.valueOf(colorLabelMap.get(value));
        }

        CSVWriter labelWriter = new CSVWriter(new FileWriter(labelEncodedFile));
        labelWriter.writeNext(header);
        labelWriter.writeAll(dataRows);
        labelWriter.close();
        System.out.println("Label Encoding done: " + colorLabelMap);

        // -------------------------
        // 3️⃣ Ordinal Encoding Example (Size)
        // -------------------------
        int sizeIndex = Arrays.asList(header).indexOf("Size");
        Map<String, Integer> sizeOrdinalMap = new HashMap<>();
        sizeOrdinalMap.put("Small", 0);
        sizeOrdinalMap.put("Medium", 1);
        sizeOrdinalMap.put("Large", 2);

        List<String[]> ordinalRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            newRow[sizeIndex] = String.valueOf(sizeOrdinalMap.get(row[sizeIndex]));
            ordinalRows.add(newRow);
        }

        CSVWriter ordinalWriter = new CSVWriter(new FileWriter(ordinalEncodedFile));
        ordinalWriter.writeNext(header);
        ordinalWriter.writeAll(ordinalRows);
        ordinalWriter.close();
        System.out.println("Ordinal Encoding done: " + sizeOrdinalMap);

        // -------------------------
        // 4️⃣ One-Hot Encoding Example (Color)
        // -------------------------
        List<String> uniqueColors = new ArrayList<>(colorLabelMap.keySet());
        Collections.sort(uniqueColors);

        List<String> newHeaderList = new ArrayList<>(Arrays.asList(header));
        newHeaderList.remove("Color");
        for (String cat : uniqueColors) newHeaderList.add("Color_" + cat);

        List<String[]> oneHotRows = new ArrayList<>();
        for (String[] row : dataRows) {
            List<String> newRow = new ArrayList<>(Arrays.asList(row));
            int originalColorValue = Integer.parseInt(row[colorIndex]);
            newRow.remove(colorIndex);

            for (String cat : uniqueColors) {
                newRow.add(colorLabelMap.get(cat) == originalColorValue ? "1" : "0");
            }
            oneHotRows.add(newRow.toArray(new String[0]));
        }

        CSVWriter oneHotWriter = new CSVWriter(new FileWriter(oneHotEncodedFile));
        oneHotWriter.writeNext(newHeaderList.toArray(new String[0]));
        oneHotWriter.writeAll(oneHotRows);
        oneHotWriter.close();
        System.out.println("One-Hot Encoding done!");

        // -------------------------
        // 5️⃣ Frequency Encoding Example (Color)
        // -------------------------
        Map<String, Integer> colorFrequencyMap = new HashMap<>();
        for (String[] row : dataRows) {
            String originalColor = null;
            for (Map.Entry<String, Integer> entry : colorLabelMap.entrySet()) {
                if (entry.getValue() == Integer.parseInt(row[colorIndex])) {
                    originalColor = entry.getKey();
                    break;
                }
            }
            colorFrequencyMap.put(originalColor, colorFrequencyMap.getOrDefault(originalColor, 0) + 1);
        }

        List<String[]> frequencyRows = new ArrayList<>();
        for (String[] row : dataRows) {
            String[] newRow = Arrays.copyOf(row, row.length);
            int originalColorValue = Integer.parseInt(row[colorIndex]);
            String originalColor = null;
            for (Map.Entry<String, Integer> entry : colorLabelMap.entrySet()) {
                if (entry.getValue() == originalColorValue) {
                    originalColor = entry.getKey();
                    break;
                }
            }
            newRow[colorIndex] = String.valueOf(colorFrequencyMap.get(originalColor));
            frequencyRows.add(newRow);
        }

        CSVWriter frequencyWriter = new CSVWriter(new FileWriter(frequencyEncodedFile));
        frequencyWriter.writeNext(header);
        frequencyWriter.writeAll(frequencyRows);
        frequencyWriter.close();
        System.out.println("Frequency Encoding done: " + colorFrequencyMap);
    }
}
