package com.adventofcode.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReaderUtil {

    public static List<String> createListFromFile(String path) {
        try {
            List<String> inputs = new ArrayList<>();

            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                inputs.add(line);
            }
            fileReader.close();

            return inputs;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Integer> createIntListFromFile(String path) {
        try {
            List<Integer> inputs = new ArrayList<>();

            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                inputs.add(Integer.valueOf(line));
            }
            fileReader.close();

            return inputs;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}