import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestTraindata {

    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/data.csv";
        String trainFile = "train_data.csv";
        String testFile = "test_data.csv";

        // 1️⃣ Read CSV
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> allRows = reader.readAll();
        reader.close();

        // 2️⃣ Separate header
        String[] header = allRows.get(0);
        List<String[]> dataRows = new ArrayList<>(allRows.subList(1, allRows.size()));

        // 3️⃣ Shuffle data randomly
        Collections.shuffle(dataRows, new Random());

        // 4️⃣ Split ratio (80% train, 20% test)
        int totalRows = dataRows.size();
        int trainSize = (int) (totalRows * 0.8);

        List<String[]> trainData = new ArrayList<>(dataRows.subList(0, trainSize));
        List<String[]> testData = new ArrayList<>(dataRows.subList(trainSize, totalRows));

        // 5️⃣ Add header to train and test data
        trainData.add(0, header);
        testData.add(0, header);

        // 6️⃣ Write train data
        CSVWriter trainWriter = new CSVWriter(new FileWriter(trainFile));
        trainWriter.writeAll(trainData);
        trainWriter.close();

        // 7️⃣ Write test data
        CSVWriter testWriter = new CSVWriter(new FileWriter(testFile));
        testWriter.writeAll(testData);
        testWriter.close();

        System.out.println("Data split completed!");
        System.out.println("Train rows: " + (trainData.size() - 1));
        System.out.println("Test rows: " + (testData.size() - 1));
    }
} // <-- Make sure this closing brace exists
