package fr.lefrem.domain.services;


import fr.lefrem.domain.models.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.lefrem.domain.services.Action.moveAdventurers;
import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void should_give_TreasureMap_after_parsing_and_verify_the_result_after_moveAdventurers() {
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
        tileMap.put(new Coordinate(3,0), new Treasure(2));
        tileMap.put(new Coordinate(3,1), new Treasure(3));
        tileMap.put(new Coordinate(3,2), new Plain());

        Adventurer adventurer1 = new Adventurer("A1", new Coordinate(1, 1), "S", "AADADAGGA");
        Adventurer adventurer2 = new Adventurer("A2", new Coordinate(0, 0), "O", "AGGAGGDDDDGAAA");
        List<Adventurer> adventurers = List.of(adventurer1, adventurer2);

        TreasureMap treasureMap = new TreasureMap(width, height, tileMap, adventurers);

        TreasureMap result = moveAdventurers(treasureMap);

        Adventurer updatedAdventurer1 = result.getAdventurers().get(0);
        assertEquals(new Coordinate(3, 0), updatedAdventurer1.getCoordinate());
        assertEquals("S", updatedAdventurer1.getOrientation());
        assertEquals(3, updatedAdventurer1.getTreasureNumber());

        Adventurer updatedAdventurer2 = result.getAdventurers().get(1);
        assertEquals(new Coordinate(2, 0), updatedAdventurer2.getCoordinate());
        assertEquals("S", updatedAdventurer2.getOrientation());
        assertEquals(0, updatedAdventurer2.getTreasureNumber());

        Tile tileAtTreasure = result.getTileMap().get(new Coordinate(3, 1));
        assertInstanceOf(Treasure.class, tileAtTreasure);
        assertEquals(2, ((Treasure) tileAtTreasure).getTreasureNumber());

        Tile tileAtPlain = result.getTileMap().get(new Coordinate(3, 0));
        assertInstanceOf(Plain.class, tileAtPlain);
    }
}