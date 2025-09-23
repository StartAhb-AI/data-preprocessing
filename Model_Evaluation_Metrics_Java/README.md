# 📊 Model Evaluation Metrics in Machine Learning

This document explains **Accuracy, Precision, Recall, F1 Score, and Confusion Matrix** with examples and Java code.  
These metrics help us measure how good a model is beyond just accuracy.

---

## 🔹 Why Evaluation Metrics?
- Accuracy alone is not always reliable.
- Models can perform differently on **imbalanced datasets**.
- We need better insights:
    - **Confusion Matrix**
    - **Precision**
    - **Recall**
    - **F1 Score**

---

## 🔹 Confusion Matrix
A table that compares **actual vs predicted** results.

|                   | Predicted Positive | Predicted Negative |
|-------------------|--------------------|--------------------|
| **Actual Positive** | True Positive (TP) | False Negative (FN) |
| **Actual Negative** | False Positive (FP) | True Negative (TN) |

📌 **Example**: Spam vs Not-Spam Emails

- TP = Correctly identified spam
- TN = Correctly identified normal emails
- FP = Normal email marked as spam (false alarm)
- FN = Spam email missed (undetected)

---

## 🔹 Accuracy
**Formula**:  
\[
Accuracy = \frac{TP + TN}{TP + TN + FP + FN}
\]

✅ Meaning: “Overall correctness”  
⚠️ Problem: Misleading if data is imbalanced.

---

## 🔹 Precision
**Formula**:  
\[
Precision = \frac{TP}{TP + FP}
\]

✅ Meaning: “When the model predicts positive, how often is it correct?”  
📌 Example: *When model says Spam, how often is it really Spam?*

---

## 🔹 Recall
**Formula**:  
\[
Recall = \frac{TP}{TP + FN}
\]

✅ Meaning: “Out of all actual positives, how many did we catch?”  
📌 Example: *Out of all Spam emails, how many did we detect?*

---

## 🔹 F1 Score
**Formula**:  
\[
F1 = 2 \times \frac{Precision \times Recall}{Precision + Recall}
\]

✅ Meaning: Balance between **Precision & Recall**  
📌 Useful when both false positives and false negatives are costly.

---

## 🔹 Java Example (Manual Calculation)
```java
public class EvaluationExample {
    public static void main(String[] args) {
        int TP = 15, TN = 70, FP = 10, FN = 5;

        double accuracy  = (double)(TP+TN)/(TP+TN+FP+FN);
        double precision = (double) TP / (TP+FP);
        double recall    = (double) TP / (TP+FN);
        double f1        = 2*(precision*recall)/(precision+recall);

        System.out.println("Accuracy: " + accuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + f1);
    }
}


 Accuracy

Formula:

𝐴𝑐𝑐𝑢𝑟𝑎𝑐𝑦 =   𝑇𝑃+𝑇𝑁/𝑇𝑃+𝑇𝑁+𝐹𝑃+𝐹𝑁

Meaning: “Overall correctness”

Works well if dataset is balanced

Problem: Misleading if one class dominates

 Precision

Formula:

𝑃𝑟𝑒𝑐𝑖𝑠𝑖𝑜𝑛=𝑇𝑃/𝑇𝑃+𝐹𝑃


Meaning: “Of all predicted positives, how many are correct?”

Example: When model says Spam, how often is it really Spam?

 Recall

Formula:

𝑅𝑒𝑐𝑎𝑙𝑙=𝑇𝑃/𝑇𝑃+𝐹𝑁



Meaning: “Of all actual positives, how many did we catch?”

Example: Out of all Spam emails, how many did we detect?

 F1 Score

Formula:
F1=2×Precision*Recall/Precision+Recall

Meaning: Balance between Precision & Recall

Used when both false positives and false negatives are costly
