package fr.lefrem.domain.services;

import fr.lefrem.domain.models.*;

import java.util.List;

public class Action {

    public static TreasureMap moveAdventurers(TreasureMap treasureMap) {
        List<Adventurer> adventurers = treasureMap.getAdventurers();
        int maxMoves = maxMoves(treasureMap.getAdventurers());

        for (int i = 0; i < maxMoves; i++) {
            for (Adventurer adventurer : adventurers) {
                if (!adventurer.getMovement().isEmpty()) {
                    switch (adventurer.getMovement().substring(0, 1)) {
                        case "A": {
                            adventurer.setCoordinate(nextPosition(treasureMap, adventurer));
                            break;
                        }
                        case "D": {
                            adventurer.setOrientation(turnRight(adventurer.getOrientation()));
                            break;
                        }
                        case "G": {
                            adventurer.setOrientation(turnLeft(adventurer.getOrientation()));
                            break;
                        }
                        default:
                            continue;
                    }
                    adventurer.setMovement(adventurer.getMovement().length() > 1 ? adventurer.getMovement().substring(1) : "");
                }
            }
        }

        return treasureMap;
    }

    private static int maxMoves(List<Adventurer> adventurers) {
        return adventurers.stream().mapToInt(it -> it.getMovement().length())
                .max()
                .orElse(0);
    }

    private static String turnLeft(String orientation) {
        return switch (orientation) {
            case "N" -> "O";
            case "S" -> "E";
            case "O" -> "S";
            case "E" -> "N";
            default -> throw new IllegalArgumentException("Illegal argument for orientation");
        };
    }

    private static String turnRight(String orientation) {
        return switch (orientation) {
            case "N" -> "E";
            case "S" -> "O";
            case "O" -> "N";
            case "E" -> "S";
            default -> throw new IllegalArgumentException("Illegal argument for orientation");
        };
    }

    private static Coordinate nextPosition(TreasureMap treasureMap, Adventurer adventurer) {
        Coordinate nexCoordinate;
        switch (adventurer.getOrientation()) {
            case "N": {
                nexCoordinate = new Coordinate(adventurer.getCoordinate().verticalPosition() - 1, adventurer.getCoordinate().horizontalPosition());
                break;
            }
            case "E": {
                nexCoordinate = new Coordinate(adventurer.getCoordinate().verticalPosition(), adventurer.getCoordinate().horizontalPosition() + 1);
                break;
            }
            case "S": {
                nexCoordinate = new Coordinate(adventurer.getCoordinate().verticalPosition() + 1, adventurer.getCoordinate().horizontalPosition());
                break;
            }
            case "O": {
                nexCoordinate = new Coordinate(adventurer.getCoordinate().verticalPosition(), adventurer.getCoordinate().horizontalPosition() - 1);
                break;
            }
            default:
                throw new IllegalArgumentException("Illegal argument for orientation");
        }
        return checkMovementIsPossible(adventurer, nexCoordinate, treasureMap);
    }

    private static Coordinate checkMovementIsPossible(Adventurer adventurer, Coordinate nextCoordinate, TreasureMap treasureMap) {
        if (nextCoordinate.horizontalPosition() < 0 || nextCoordinate.horizontalPosition() >= treasureMap.getWidth() || nextCoordinate.verticalPosition() < 0 || nextCoordinate.verticalPosition() >= treasureMap.getHeight()) {
            return adventurer.getCoordinate();
        }
        List<Coordinate> list = treasureMap.getAdventurers().stream().map(Adventurer::getCoordinate).toList();
        if (list.contains(nextCoordinate)) {
            return adventurer.getCoordinate();
        }
        if (treasureMap.getTileMap().get(nextCoordinate).isObstacle()) {
            return adventurer.getCoordinate();
        }
        if (treasureMap.getTileMap().get(nextCoordinate) instanceof Treasure treasureTile) {
            treasureTile.setTreasureNumber(treasureTile.getTreasureNumber() -1 );
            adventurer.setTreasureNumber(adventurer.getTreasureNumber() + 1);
            if (treasureTile.getTreasureNumber() == 0) {
                treasureMap.getTileMap().replace(nextCoordinate, new Plain());
            }
        }
        return nextCoordinate;
    }
}
