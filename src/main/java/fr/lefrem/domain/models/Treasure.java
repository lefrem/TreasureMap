package fr.lefrem.domain.models;

public final class Treasure implements Tile {
    private int treasureNumber;

    public Treasure(int treasureNumber) {
        this.treasureNumber = treasureNumber;
    }

    @Override
    public boolean isObstacle() {
        return false;
    }

    public int getTreasureNumber() {
        return treasureNumber;
    }

    public void setTreasureNumber(int treasureNumber) {
        this.treasureNumber = treasureNumber;
    }
}
