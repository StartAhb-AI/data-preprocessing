package org.example;
import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

record Employee(String name, int age, double salary, String department) {}

public class EmployeesCSVWriter {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee("Anusha", 25, 55000.75, "Engineering"),
                new Employee("Ravi", 30, 72000.00, "Finance"),
                new Employee("Priya", 28, 64000.50, "HR")
        );

        String fileName = "data1.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            // Write header
            String[] header = {"Name", "Age", "Salary", "Department"};
            writer.writeNext(header);

            // Write employee data
            for (Employee emp : employees) {
                String[] row = {
                        emp.name(),
                        String.valueOf(emp.age()),
                        String.valueOf(emp.salary()),
                        emp.department()
                };
                writer.writeNext(row);
            }

            System.out.println("✅ Employee data written to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
