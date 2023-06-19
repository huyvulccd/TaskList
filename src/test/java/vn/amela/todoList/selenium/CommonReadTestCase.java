package vn.amela.todoList.selenium;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonReadTestCase {
    private static final String URL_COMMON_FOLDER_EXIST_TESTCASE = "D:\\Code\\Java\\TaskList\\src\\test\\java\\vn\\amela\\todoList\\selenium"; // paste your absolute path here.

    public static List<String> getTestCase(String url) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(URL_COMMON_FOLDER_EXIST_TESTCASE + url));
            List<String> cases = new ArrayList<>();
            try {
                String line = reader.readLine();
                while (line != null) {
                    cases.add(line);
                    line = reader.readLine();
                }
                reader.close();
                return cases;
            } catch (IOException e) {
                reader.close();
                return Collections.emptyList();
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static void reportToText(String url, String nameFile, List<Boolean> reports) {
        int i = 1;
        try {
            BufferedWriter reader = new BufferedWriter(new FileWriter(URL_COMMON_FOLDER_EXIST_TESTCASE + url + "\\report_" + nameFile +".txt"));
            for (Boolean report: reports) {
                reader.write("case " + i++ + ": " + (report ? "passed" : "failed"));
                reader.newLine();
            }
        } catch (IOException ignored) {

        }
    }
}

