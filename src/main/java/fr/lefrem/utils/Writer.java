package fr.lefrem.utils;

import fr.lefrem.domain.models.Mountain;
import fr.lefrem.domain.models.Treasure;
import fr.lefrem.domain.models.TreasureMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Writer {
    public static void writingFile(Path path, TreasureMap treasureMap) {
        try {
            StringBuilder content = new StringBuilder();

            content.append("C - ")
                    .append(treasureMap.getWidth())
                    .append(" - ")
                    .append(treasureMap.getHeight())
                    .append("\n");


            treasureMap.getTileMap().forEach((key, value) -> {
                if (value instanceof Mountain) {
                    content.append("M - ")
                            .append(key.horizontalPosition())
                            .append(" - ")
                            .append(key.verticalPosition()).
                            append("\n");
                }

                if (value instanceof Treasure treasureTile) {
                    content.append("T - ")
                            .append(key.horizontalPosition())
                            .append(" - ")
                            .append(key.verticalPosition())
                            .append(" - ")
                            .append(treasureTile.getTreasureNumber())
                            .append("\n");
                }
            });

            treasureMap.getAdventurers().forEach(it -> content.append("A - ")
                    .append(it.getName())
                    .append(" - ")
                    .append(it.getCoordinate().horizontalPosition())
                    .append(" - ")
                    .append(it.getCoordinate().verticalPosition())
                    .append(" - ")
                    .append(it.getOrientation())
                    .append(" - ")
                    .append(it.getTreasureNumber())
                    .append("\n"));

            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException("Error during writing in file : " + e.getMessage());
        }
    }
}
