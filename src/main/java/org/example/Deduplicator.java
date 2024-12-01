package org.example;

import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

import static org.example.TextUtility.canonicalizeString;
import static org.example.TextUtility.computeMinHash;

public class Deduplicator {


    public Deduplicator() {

    }

    public void deduplicate(Map<String, String> texts, int minHashNumber) {
        // Генерация шинглов и хэшей
        Map<String, Set<Shingle>> shinglesMap = new HashMap<>();

        for (Map.Entry<String, String> entry : texts.entrySet()) {
            String id = entry.getKey();
            String text = entry.getValue();

            Set<Shingle> shingles = createShingles(text, 2, minHashNumber); // Шинглы длиной 2
            shinglesMap.put(id, shingles);
        }
        List<String> textKeys = shinglesMap.keySet().stream().toList();
        // Сравнение хэшей
        for (int i = 0; i < textKeys.size() - 1; i++) {
            for (int j = 1; j < textKeys.size(); j++) {
                if (i != j) {
                    String fileNameFirst = textKeys.get(i);
                    String fileNameSecond = textKeys.get(j);
                    Set<Shingle> shinglesFirstFile = shinglesMap.get(fileNameFirst);
                    Set<Shingle> shinglesSecondFile = shinglesMap.get(fileNameSecond);
                    for (Shingle shingle : shinglesFirstFile) {
                        if (shinglesSecondFile.contains(shingle)) {
                            System.out.println("Найдено совпадение шингла файла " + fileNameFirst + " c файлом " + fileNameSecond + " . Шингл - " + shingle);
                        }
                    }
                }
            }
        }
    }

    private Set<Shingle> createShingles(String text, int wordsCount, int minHashNumber) {
        Set<Shingle> shingles = new HashSet<>();
        String[] words = text.split("\s+");
        for (int i = 0; i <= words.length - wordsCount; i++) {
            StringBuilder shingle = new StringBuilder();
            for (int j = 0; j < wordsCount; j++) {
                shingle.append(words[i + j]).append(" ");
            }
            String shingleText = shingle.toString().trim();
            Shingle shingletoAdd = new Shingle(shingleText, computeMinHash(shingleText, minHashNumber));
            shingles.add(shingletoAdd);
        }
        return shingles;
    }

}
