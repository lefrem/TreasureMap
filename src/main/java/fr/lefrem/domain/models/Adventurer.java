package fr.lefrem.domain.models;

import java.util.Objects;

public class Adventurer {
    private String name;
    private Coordinate coordinate;
    private String orientation;
    private String movement;
    private int treasureNumber;

    public Adventurer(String name, Coordinate coordinate, String orientation, String movement) {
        this.name = name;
        this.coordinate = coordinate;
        this.orientation = orientation;
        this.movement = movement;
        this.treasureNumber = 0;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public int getTreasureNumber() {
        return treasureNumber;
    }

    public void setTreasureNumber(int treasureNumber) {
        this.treasureNumber = treasureNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adventurer that = (Adventurer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
