package com.ai;
//DL4J → real ML library, useful for actual ML projects.
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Dl4jEvaluation {
    public static void main(String[] args) {
        // Actual labels (one-hot encoded)
        INDArray labels = Nd4j.create(new double[][]{
                {1,0}, {0,1}, {1,0}, {1,0}, {0,1}
        });

        // Predictions (one-hot encoded)
        INDArray predictions = Nd4j.create(new double[][]{
                {1,0}, {0,1}, {1,0}, {0,1}, {0,1}
        });

        // Evaluation
        Evaluation eval = new Evaluation(2); // 2 classes
        eval.eval(labels, predictions);

        // Print results
        System.out.println(eval.stats());
        System.out.println("Accuracy: " + eval.accuracy());
        System.out.println("Precision: " + eval.precision());
        System.out.println("Recall: " + eval.recall());
        System.out.println("F1 Score: " + eval.f1());
    }
}

