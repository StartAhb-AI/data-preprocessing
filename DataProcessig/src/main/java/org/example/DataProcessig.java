package org.example;

import smile.data.DataFrame;
import smile.data.vector.StringVector;
import smile.feature.Standardizer;
import smile.io.Read;
import java.nio.file.Paths;

public class DataProcessig {
    public static void main(String[] args) throws Exception {
        DataFrame iris = Read.arff(Paths.get("data/iris.arff"));

        System.out.println("Original Schema:");
        System.out.println(iris.schema());

        String[] featureNames = {"sepallength", "sepalwidth", "petallength", "petalwidth"};
        String labelName = "class";

        StringVector labels = iris.stringVector(labelName);
        // int[] y = labels.factor().toIntArray();
        int[] y = labels.toIntArray();

        double[][] X = iris.select(featureNames).toArray();

        //Standardizer scaler = new Standardizer();
        //scaler.learn(X);
        //scaler.transform(X);

        Standardizer scaler = Standardizer.fit(X);
        X = scaler.transform(X);


        int n = X.length;
        int trainSize = (int) (n * 0.8);

        int[] index = smile.math.MathEx.permutate(n);
        double[][] Xtrain = new double[trainSize][];
        double[][] Xtest = new double[n - trainSize][];
        int[] ytrain = new int[trainSize];
        int[] ytest = new int[n - trainSize];

        for (int i = 0; i < trainSize; i++) {
            Xtrain[i] = X[index[i]];
            ytrain[i] = y[index[i]];
        }
        for (int i = trainSize; i < n; i++) {
            Xtest[i - trainSize] = X[index[i]];
            ytest[i - trainSize] = y[index[i]];
        }

        System.out.println("\nFirst 5 preprocessed rows:");
        for (int i = 0; i < 5; i++) {
            System.out.print("X: ");
            for (double v : Xtrain[i]) System.out.printf("%.3f ", v);
            System.out.println(" | Label: " + ytrain[i]);
        }
    }
}
