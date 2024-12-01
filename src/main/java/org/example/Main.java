package org.example;

import java.io.IOException;
import java.util.Map;

import static org.example.TextUtility.readTextFiles;


public class Main {
    public static void main(String[] args) throws IOException {
        String directoryPath = "src/main/resources";
        int minHashNumber = 10;
        Deduplicator deduplicator = new Deduplicator();
        Map<String, String> texts = readTextFiles(directoryPath);
        deduplicator.deduplicate(texts, minHashNumber);
    }
}