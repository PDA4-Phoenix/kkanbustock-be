package com.bull4jo.kkanbustock.group.domain.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupNameGenerator {
    private List<String> adjectiveList;
    private List<String> nounList;
    private final Random random = new Random();

    public GroupNameGenerator() throws IOException {
        loadResources();
    }

    private void loadResources() throws IOException {
        adjectiveList = loadResourceFile("groupname/adjective.txt");
        nounList = loadResourceFile("groupname/noun.txt");
    }

    private List<String> loadResourceFile(String resourcePath) throws IOException {
        List<String> result = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            }
        }
        return result;
    }

    public String generateGroupName() {
        if (adjectiveList.isEmpty() || nounList.isEmpty()) {
            throw new IllegalStateException("Resource files are empty or not found.");
        }

        String adjective = adjectiveList.get(random.nextInt(adjectiveList.size()));
        String noun = nounList.get(random.nextInt(nounList.size()));
        return adjective + " " + noun;
    }
}
