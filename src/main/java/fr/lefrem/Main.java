package fr.lefrem;

import fr.lefrem.domain.models.TreasureMap;

import java.nio.file.Path;
import java.nio.file.Paths;

import static fr.lefrem.domain.services.Action.moveAdventurers;
import static fr.lefrem.utils.Parser.readingFile;
import static fr.lefrem.utils.Writer.writingFile;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Only two argument : input and output file");
        }

        Path pathIn = Paths.get(args[0]);
        Path pathOut = Paths.get(args[1]);

        TreasureMap treasureMap = readingFile(pathIn);
        TreasureMap treasureMapResult = moveAdventurers(treasureMap);

        writingFile(pathOut, treasureMapResult);
    }
}