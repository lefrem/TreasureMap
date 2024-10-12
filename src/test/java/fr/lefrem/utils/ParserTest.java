package fr.lefrem.utils;

import fr.lefrem.domain.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Paths;

import static fr.lefrem.utils.Parser.readingFile;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void should_read_file_and_parse_is_successful() {
        TreasureMap result = readingFile(Paths.get("src/test/java/resources/in/mapSuccessful.txt"));

        assertNotNull(result);
        assertEquals(5, result.getWidth());
        assertEquals(5, result.getHeight());
        Tile tile = result.getTileMap().get(new Coordinate(1, 1));
        assertInstanceOf(Mountain.class, tile);
        tile = result.getTileMap().get(new Coordinate(2, 2));
        assertInstanceOf(Treasure.class, tile);
        assertEquals(new Adventurer("Adventurer", new Coordinate(1, 3), "N", "AAGA"), result.getAdventurers().get(0));
        assertEquals(new Adventurer("Adventurer2", new Coordinate(3, 3), "N", "AAGA"), result.getAdventurers().get(1));
    }

    @Test
    void should_read_file_but_the_first_character_is_not_c() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            readingFile(Paths.get("src/test/java/resources/in/mapWithoutFirstCharacterC.txt"));
        });

        assertEquals("First character for defined treasure map is not present", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/java/resources/in/mapInWithMoreOf3ArgumentForC.txt",
            "src/test/java/resources/in/mapInWithLessOf3ArgumentForC.txt",
            "src/test/java/resources/in/mapInWithMoreOf3ArgumentForM.txt",
            "src/test/java/resources/in/mapInWithLessOf3ArgumentForM.txt",
            "src/test/java/resources/in/mapInWithMoreOf4ArgumentForT.txt",
            "src/test/java/resources/in/mapInWithLessOf4ArgumentForT.txt",
            "src/test/java/resources/in/mapInWithMoreOf6ArgumentForA.txt",
            "src/test/java/resources/in/mapInWithLessOf6ArgumentForA.txt",
    })
    void should_read_file_but_do_not_have_right_number_argument(String path) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            readingFile(Paths.get(path));
        });

        assertEquals("There is an error on the number of arguments", exception.getMessage());
    }

    @Test
    void should_try_read_file_but_path_is_null() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            readingFile(Paths.get(""));
        });

        assertEquals("Error during reading file : ", exception.getMessage());
    }

    @Test
    void should_read_file_but_have_two_date_for_same_tile() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            readingFile(Paths.get("src/test/java/resources/in/mapWithDataDuplicate.txt"));
        });

        assertEquals("Data duplicated in file", exception.getMessage());
    }

}