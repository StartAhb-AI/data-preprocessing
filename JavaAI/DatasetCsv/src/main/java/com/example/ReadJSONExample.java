package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJSONExample {
    public static void main(String[] args) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get("src/main/resources/people.json")));
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("Name");
                int age = obj.getInt("Age");
                String city = obj.getString("City");

                System.out.println(name + ", " + age + ", " + city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
