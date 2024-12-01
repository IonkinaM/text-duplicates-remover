package org.example;

import org.example.service.Deduplicator;

import java.io.IOException;
import java.util.Map;

import static org.example.utility.TextUtility.readTextFiles;


public class Main {
    public static void main(String[] args) throws IOException {
        String directoryPath = "src/main/resources";
        int hashIteratesNumber = 10;
        int shingleSize = 2;
        Deduplicator deduplicator = new Deduplicator();
        Map<String, String> texts = readTextFiles(directoryPath);
        deduplicator.deduplicate(texts, hashIteratesNumber,shingleSize);
    }
}