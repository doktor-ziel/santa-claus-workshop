package com.griddynamics.tech3camp.santa.claus.workshop.digression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionary {
    private final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private final Map<String, String> translations;

    public Dictionary(String resourceName) {
        this.translations = readFile(resourceName);
    }

    private Map<String, String> readFile(String resourceName) {
        try (InputStream in = getClass().getResourceAsStream(resourceName);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.lines()
                    .map(this::parseLine)
                    .filter(a -> a.length > 0)
                    .collect(Collectors.toMap(a -> a[0], a -> a[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String[] parseLine(String line) {
        if (line.contains(":")) {
            String[] result = line.split(":");
            if (result.length != 2) {
                return new String[]{};
            } else {
                result[0] = result[0].trim();
                result[1] = result[1].trim();
                return result;
            }
        } else {
            return new String[]{};
        }
    }

    public String translate(String word) {
        return translations.getOrDefault(word, word);
    }
}
