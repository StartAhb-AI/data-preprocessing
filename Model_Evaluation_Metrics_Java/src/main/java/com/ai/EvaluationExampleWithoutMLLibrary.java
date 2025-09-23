package com.ai;
//Plain Java → no setup needed, best for learning.
public class EvaluationExampleWithoutMLLibrary {
    public static void main(String[] args) {
        // Actual vs Predicted values
        int[] actual    = {1, 0, 1, 1, 0, 1, 0, 0, 1, 0};
        int[] predicted = {1, 0, 1, 0, 0, 1, 1, 0, 1, 0};

        int TP = 0, TN = 0, FP = 0, FN = 0;

        for (int i = 0; i < actual.length; i++) {
            if (actual[i] == 1 && predicted[i] == 1) TP++;
            else if (actual[i] == 0 && predicted[i] == 0) TN++;
            else if (actual[i] == 0 && predicted[i] == 1) FP++;
            else if (actual[i] == 1 && predicted[i] == 0) FN++;
        }

        double accuracy  = (double)(TP + TN) / (TP + TN + FP + FN);
        double precision = (TP + FP) == 0 ? 0 : (double) TP / (TP + FP);
        double recall    = (TP + FN) == 0 ? 0 : (double) TP / (TP + FN);
        double f1        = (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);

        System.out.println("Confusion Matrix:");
        System.out.println("TP=" + TP + " FN=" + FN);
        System.out.println("FP=" + FP + " TN=" + TN);
        System.out.println("Accuracy: " + accuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + f1);
    }
}
