package com.epam.rd.autotasks;

import java.util.*;
import java.util.List;


public class Words {
    private static final int MIN_LENGTH = 4;
    private static final int MIN_OCCURRENCE = 10;

    public String countWords(List<String> lines) {
        Map<String, Integer> wordsCount = new HashMap<>();

        //remove disallowed characters, and save only words with 4 characters, then I save it in a hashMap with key=word value=total occurrences
        for (String line : lines) {
            line = line.replaceAll("[^\\p{L}\\p{Nd}\\s]+", " ").toLowerCase();
            //valid words with no more than maxQuantity characters
            List<String> words = getValidWords(line);
            for (String word : words) {
                wordsCount.put(word, wordsCount.getOrDefault(word, 0) + 1);
            }
        }

        //I save it in an arrayList to order it because in an arrayList the sorting operation is faster
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordsCount.entrySet());
        Collections.sort(sortedWords, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                int result = e2.getValue().compareTo(e1.getValue());
                if (result == 0) {
                    result = e1.getKey().compareTo(e2.getKey());
                }
                return result;
            }
        });


        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> word : sortedWords) {
            if (word.getValue() >= MIN_OCCURRENCE) {
                result.append(word.getKey()).append(" - ").append(word.getValue()).append("\n");
            }
        }

        return result.substring(0, result.length() - 1);
    }

    private static List<String> getValidWords(String line) {
        List<String> words = new ArrayList<>();
        String[] split = line.split("\\s+");
        for (String word : split) {
            if (word.length() >= MIN_LENGTH) {
                words.add(word);
            }
        }
        return words;
    }

}
