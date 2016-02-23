package gameData.Battleships;

import gameData.enums.Orientation;
import gameData.Boards.AlexaBoard;
import gameData.GameCommon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by corentinl on 2/2/16.
 */
public class ShipGenerator {
    public static List<Battleship> generateDefaultShips() {
        return generateRandomShips(GameCommon.minNumberRows, GameCommon.minNumberColumns, 1);
    }

    public static List<Battleship> generateRandomShips(int nbRows, int nbColumns, int numberOfShips) {
        List<Integer> shipSizes = randomSizes(nbRows, nbColumns, numberOfShips);

        if (!placementIsPossible(nbRows, nbColumns, shipSizes)) {
            return null;
        }

        List<Battleship> battleships = new ArrayList<Battleship>();

        AlexaBoard tempBoard = new AlexaBoard(nbRows, nbColumns);

        for (int i = 0 ; i < numberOfShips ; i++) {
            Battleship newShip = null;
            int numberOfIterations = 0;
            while (numberOfIterations < GameCommon.smallNumberMaxIterations
                && newShip == null) {
                newShip = generateAndAddOneShip(nbRows, nbColumns, shipSizes.get(i), tempBoard);
            }

            if (newShip == null || !tempBoard.addBattleship(newShip)) {
                return null;
            }

            battleships.add(newShip);
        }

        //System.out.println(tempBoard);

        return battleships;
    }

    private static List<Integer> randomSizes(int nbRows, int nbColumns, int numberOfShips) {
        List<Integer> sizes = new ArrayList<Integer>();

        int maxSize = Math.min(Math.min(nbRows, nbColumns), GameCommon.maxShipSize);
        for (int i = 0 ; i < numberOfShips ; i++) {
            sizes.add(ThreadLocalRandom.current().nextInt(GameCommon.minShipSize, maxSize));
        }

        return sizes;
    }

    private static Battleship generateAndAddOneShip(int nbRows, int nbColumns, int shipSize, AlexaBoard tempBoard) {
        Point origin = randomCoordinates(nbRows, nbColumns, shipSize);
        Orientation orientation = randomOrientation(nbRows, nbColumns, origin, shipSize);
        Battleship randomBattleship = new Battleship(origin, shipSize, orientation);

        //System.out.println(randomBattleship);

        if (tempBoard.checkSpaceIsFree(randomBattleship)) {
            return randomBattleship;
        } else {
            return null;
        }
    }

    private static Point randomCoordinates(int nbRows, int nbColumns, int shipSize) {
        int x = ThreadLocalRandom.current().nextInt(0, nbRows);
        int y = ThreadLocalRandom.current().nextInt(0, nbColumns);

        int numberOfIterations = 0;

        while (numberOfIterations < GameCommon.bigNumberMaxIterations
                && (x + shipSize > nbColumns && y + shipSize > nbRows)) {
            x--;
            y--;
            numberOfIterations++;
        }

        return new Point(x, y);
    }

    private static Orientation randomOrientation(int nbRows, int nbColumns, Point origin, int shipSize) {
        List<Orientation> possibleOrientation = new ArrayList<Orientation>();

        if (origin.y + shipSize <= nbColumns) {
            possibleOrientation.add(Orientation.HORIZONTAL);
        }
        if (origin.x + shipSize <= nbRows) {
            possibleOrientation.add(Orientation.VERTICAL);
        }

        //System.out.println(possibleOrientation);

        int pick = ThreadLocalRandom.current().nextInt(0, possibleOrientation.size());
        return possibleOrientation.get(pick);
    }

    private static boolean placementIsPossible(int nbRows, int nbColumns, List<Integer>shipSizes) {
        if (shipSizes.size() >= nbRows) {
            return false;
        }

        for (int i = 0 ; i < shipSizes.size() ; i++) {
            if (shipSizes.get(i) >= nbColumns || shipSizes.get(i) >= nbRows) {
                return false;
            }
        }

        return true;
    }
}
