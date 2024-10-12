package fr.lefrem.domain.models;

import java.util.List;
import java.util.Map;

public class TreasureMap {
    private int width;
    private int height;
    private Map<Coordinate, Tile> tileMap;
    private List<Adventurer> adventurers;

    public TreasureMap(int width, int height, Map<Coordinate, Tile> tileMap, List<Adventurer> adventurers) {
        this.height = height;
        this.width = width;
        this.tileMap = tileMap;
        this.adventurers = adventurers;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (!this.tileMap.containsKey(new Coordinate(i, j))) {
                    this.tileMap.put(coordinate, new Plain());
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Coordinate, Tile> getTileMap() {
        return tileMap;
    }

    public List<Adventurer> getAdventurers() {
        return adventurers;
    }
}
