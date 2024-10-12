package fr.lefrem.domain.models;

public final class Mountain implements Tile {

    @Override
    public boolean isObstacle() {
        return true;
    }
}
