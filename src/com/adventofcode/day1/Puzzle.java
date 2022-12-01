package com.adventofcode.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.adventofcode.helper.InputReaderUtil.createIntListFromFile;

public class Puzzle {
    public static void main(String[] args) {
        List<Integer> input = createIntListFromFile("src/com/adventofcode/day1/input.txt");
        ArrayList<Integer> sums = new ArrayList<>();

        int temp = 0;
        for (int calorie : input) {
            if (calorie != 0) {
                temp += calorie;
            } else {
                sums.add(temp);
                temp = 0;
            }
        }

        Collections.sort(sums);
        Collections.reverse(sums);

        System.out.println(sums.get(0));
        System.out.println(sums.get(0) + sums.get(1) + sums.get(2));
    }
}
