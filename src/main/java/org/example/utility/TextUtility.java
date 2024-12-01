package org.example.utility;

import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class TextUtility {
    private static final Pattern ALLOW_CHARACTERS = Pattern.compile("[^a-zа-яёЁ\\s]");

    public static String canonicalizeString(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        text = text.toLowerCase().replaceAll(",|\\.", " ").trim();
        text = ALLOW_CHARACTERS.matcher(text).replaceAll("");
        return text.toLowerCase().trim();
    }

    public static String computeStringMinHash(String text, int hashIteratesNumber) {
        List<Long> hashes = new ArrayList<>();
        for (int i = 0; i < hashIteratesNumber; i++) {
            byte[] shingleBytes = text.getBytes();
            hashes.add(MurmurHash2.hash64(shingleBytes, shingleBytes.length, i));
        }
        Collections.sort(hashes);
        return Long.toString(hashes.get(0)); // Минимальный хэш
    }

    public static Map<String, String> readTextFiles(String directoryPath) throws IOException {
        Map<String, String> textFiles = new HashMap<>();
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(path -> {
                    try {
                        textFiles.put(path.getFileName().toString(), canonicalizeString(Files.readString(path)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return textFiles;
    }
}
