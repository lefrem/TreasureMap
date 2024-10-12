package fr.lefrem.domain.models;

public final class Plain implements Tile {

    @Override
    public boolean isObstacle() {
        return false;
    }
}
