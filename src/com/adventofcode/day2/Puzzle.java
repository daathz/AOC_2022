package com.adventofcode.day2;

import java.util.List;

import static com.adventofcode.helper.InputReaderUtil.createListFromFile;

public class Puzzle {

    public static void mainOld(String[] args) {
        List<String> input = createListFromFile("src/com/adventofcode/day2/input.txt");

        int score = 0;

        for (String match : input) {
            String[] hands = match.split(" ");
            String enemy = hands[0];
            String me = hands[1];

            if (enemy.equals("A")) {
                if (me.equals("X")) {
                    score += 3 + 1;
                } else if (me.equals("Y")) {
                    score += 2 + 6;
                } else {
                    score += 3 + 0;
                }
            } else if (enemy.equals("B")) {
                if (me.equals("X")) {
                    score += 0 + 1;
                } else if (me.equals("Y")) {
                    score += 2 + 3;
                } else {
                    score += 3 + 6;
                }
            } else {
                if (me.equals("X")) {
                    score += 6 + 1;
                } else if (me.equals("Y")) {
                    score += 2 + 0;
                } else {
                    score += 3 + 3;
                }
            }
        }

        System.out.println(score);
    }

    public static void main(String[] args) {
        List<String> input = createListFromFile("src/com/adventofcode/day2/input.txt");

        int score = 0;

        for (String match : input) {
            String[] hands = match.split(" ");
            String enemy = hands[0];
            String me = hands[1];

            if (enemy.equals("A")) {
                if (me.equals("X")) {
                    score += 3 + 0;
                } else if (me.equals("Y")) {
                    score += 1 + 3;
                } else {
                    score += 6 + 2;
                }
            } else if (enemy.equals("B")) {
                if (me.equals("X")) {
                    score += 1 + 0;
                } else if (me.equals("Y")) {
                    score += 2 + 3;
                } else {
                    score += 6 + 3;
                }
            } else {
                if (me.equals("X")) {
                    score += 2 + 0;
                } else if (me.equals("Y")) {
                    score += 3 + 3;
                } else {
                    score += 6 + 1;
                }
            }
        }

        System.out.println(score);
    }
}
