package fr.lefrem.utils;

import fr.lefrem.domain.models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static TreasureMap readingFile(Path path) {
        Map<Coordinate, Tile> map = Map.of();
        List<Adventurer> adventurers = new ArrayList<>();
        boolean firstReading = true;
        int height = 0;
        int width = 0;

        try {
            for (String it : Files.readAllLines(path)) {
                if (it.startsWith("#")) {
                    continue;
                }

                String[] parts = it.split("\\s*-\\s*");

                if (!parts[0].contains("C") && firstReading) {
                    throw new IllegalArgumentException("First character for defined treasure map is not present");
                }

                switch (parts[0]) {
                    case "C": {
                        checkStructureData(parts, 3);
                        width = Integer.parseInt(parts[1]);
                        height = Integer.parseInt(parts[2]);
                        map = new HashMap<>(width * height);
                        firstReading = false;
                        break;
                    }
                    case "M": {
                        checkStructureData(parts, 3);
                        checkDataPresent(map, new Coordinate(Integer.parseInt(parts[2]), Integer.parseInt(parts[1])));
                        map.put(new Coordinate(Integer.parseInt(parts[2]), Integer.parseInt(parts[1])), new Mountain());
                        break;
                    }
                    case "T": {
                        checkStructureData(parts, 4);
                        checkDataPresent(map, new Coordinate(Integer.parseInt(parts[2]), Integer.parseInt(parts[1])));
                        map.put(new Coordinate(Integer.parseInt(parts[2]), Integer.parseInt(parts[1])), new Treasure(Integer.parseInt(parts[3])));
                        break;
                    }
                    case "A": {
                        checkStructureData(parts, 6);
                        checkDataPresent(map, new Coordinate(Integer.parseInt(parts[3]), Integer.parseInt(parts[2])));
                        checkDataPresent(adventurers, new Coordinate(Integer.parseInt(parts[3]), Integer.parseInt(parts[2])));
                        adventurers.add(new Adventurer(parts[1], new Coordinate(Integer.parseInt(parts[3]), Integer.parseInt(parts[2])), parts[4], parts[5]));
                        break;
                    }
                    default:
                        continue;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Error during reading file : " + e.getMessage());
        }

        return new TreasureMap(width, height, map, adventurers);
    }

    private static void checkDataPresent(Map<Coordinate, Tile> map, Coordinate coordinate) {
        if (map.containsKey(coordinate)) {
            throw new IllegalArgumentException("Data duplicated in file");
        }
    }

    private static void checkDataPresent(List<Adventurer> adventurers, Coordinate coordinate) {
        if (adventurers.stream().anyMatch(it -> it.getCoordinate().equals(coordinate))) {
            throw new IllegalArgumentException("Data duplicated in file");
        }
    }

    private static void checkStructureData(String[] parts, int maxLengthData) {
        if (parts.length != maxLengthData) {
            throw new IllegalArgumentException("There is an error on the number of arguments");
        }
    }
}
