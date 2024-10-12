package fr.lefrem.domain.models;

public sealed interface Tile permits Plain, Mountain, Treasure {
    boolean isObstacle();
}
