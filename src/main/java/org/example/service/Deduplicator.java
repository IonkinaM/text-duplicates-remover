package org.example.service;

import org.example.dto.Shingle;

import java.util.*;

import static org.example.utility.TextUtility.computeStringMinHash;

public class Deduplicator {


    public Deduplicator() {

    }

    public void deduplicate(Map<String, String> texts, int hashIteratesNumber, int shingleSize) {
        // Генерация шинглов и хэшей
        Map<String, Set<Shingle>> shinglesMap = new HashMap<>();
        List<String> textKeys = new ArrayList<>();
        for (Map.Entry<String, String> entry : texts.entrySet()) {
            String id = entry.getKey();
            textKeys.add(id);
            String text = entry.getValue();

            Set<Shingle> shingles = createShingles(text, shingleSize, hashIteratesNumber); // Шинглы длиной shingleSize
            shinglesMap.put(id, shingles);
        }
        // Сравнение хэшей
        for (int i = 0; i < textKeys.size() - 1; i++) {
            for (int j = 1; j < textKeys.size(); j++) {
                if (i != j) {
                    String fileNameFirst = textKeys.get(i);
                    String fileNameSecond = textKeys.get(j);
                    Set<Shingle> shinglesFirstFile = shinglesMap.get(fileNameFirst);
                    Set<Shingle> shinglesSecondFile = shinglesMap.get(fileNameSecond);
                    for (Shingle shingleFirstFile : shinglesFirstFile) {
                        if (shinglesSecondFile.contains(shingleFirstFile)) {
                            System.out.println("Найдено совпадение min хэша шингла файла " + fileNameFirst + " c файлом " + fileNameSecond + " . Шингл - " + shingleFirstFile);
                        }
                    }
                }
            }
        }
    }

    private Set<Shingle> createShingles(String text, int wordsCount, int hashIteratesNumber) {
        Set<Shingle> shingles = new HashSet<>();
        String[] words = text.split("\s+");
        for (int i = 0; i <= words.length - wordsCount; i++) {
            StringBuilder shingle = new StringBuilder();
            for (int j = 0; j < wordsCount; j++) {
                shingle.append(words[i + j]).append(" ");
            }
            String shingleText = shingle.toString().trim();
            Shingle shingletoAdd = new Shingle(shingleText, computeStringMinHash(shingleText, hashIteratesNumber));
            shingles.add(shingletoAdd);
        }
        return shingles;
    }

}
