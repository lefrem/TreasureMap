package fr.lefrem.utils;

import fr.lefrem.domain.models.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.lefrem.utils.Writer.writingFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WriterTest {

    @Test
    void should_test_if_writingFile_write_correct_result() throws IOException {
        Path pathOut = Paths.get("src/test/java/resources/out/mapOut.txt");

        int width = 3;
        int height = 4;

        Map<Coordinate, Tile> tileMap = new HashMap<>(width*height);
        tileMap.put(new Coordinate(0,0), new Plain());
        tileMap.put(new Coordinate(0,1), new Mountain());
        tileMap.put(new Coordinate(0,2), new Plain());
        tileMap.put(new Coordinate(1,0), new Plain());
        tileMap.put(new Coordinate(1,1), new Plain());
        tileMap.put(new Coordinate(1,2), new Mountain());
        tileMap.put(new Coordinate(2,0), new Plain());
        tileMap.put(new Coordinate(2,1), new Plain());
        tileMap.put(new Coordinate(2,2), new Plain());
        tileMap.put(new Coordinate(3,0), new Plain());
        tileMap.put(new Coordinate(3,1), new Treasure(2));
        tileMap.put(new Coordinate(3,2), new Plain());

        Adventurer adventurer1 = new Adventurer("Lara", new Coordinate(3, 0), "S", "AADADAGGA");
        adventurer1.setTreasureNumber(3);
        List<Adventurer> adventurers = List.of(adventurer1);

        TreasureMap treasureMap = new TreasureMap(width, height, tileMap, adventurers);

        String expected = """
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 1 - 3 - 2
                A - Lara - 0 - 3 - S - 3
                """;

        writingFile(pathOut, treasureMap);

        String result = Files.readString(pathOut);

        assertEquals(expected, result);
    }

    @Test
    void should_try_write_in_file_but_path_is_null() {
        int width = 3;
        int height = 4;

        Map<Coordinate, Tile> tileMap = new HashMap<>(width*height);
        tileMap.put(new Coordinate(0,0), new Plain());
        tileMap.put(new Coordinate(0,1), new Mountain());
        tileMap.put(new Coordinate(0,2), new Plain());
        tileMap.put(new Coordinate(1,0), new Plain());
        tileMap.put(new Coordinate(1,1), new Plain());
        tileMap.put(new Coordinate(1,2), new Mountain());
        tileMap.put(new Coordinate(2,0), new Plain());
        tileMap.put(new Coordinate(2,1), new Plain());
        tileMap.put(new Coordinate(2,2), new Plain());
        tileMap.put(new Coordinate(3,0), new Plain());
        tileMap.put(new Coordinate(3,1), new Treasure(2));
        tileMap.put(new Coordinate(3,2), new Plain());

        Adventurer adventurer1 = new Adventurer("Lara", new Coordinate(3, 0), "S", "AADADAGGA");
        adventurer1.setTreasureNumber(3);
        List<Adventurer> adventurers = List.of(adventurer1);

        TreasureMap treasureMap = new TreasureMap(width, height, tileMap, adventurers);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            writingFile(Paths.get(""), treasureMap);
        });

        assertEquals("Error during writing in file : ", exception.getMessage());
    }
}