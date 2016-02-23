package gameData.fireAlgorithms;

import gameData.enums.TileState;
import gameData.Boards.BoardAbstract;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by corentinl on 2/14/16.
 */
public class FireAlgorithmRandom extends FireAlgorithmAbstract {
    public FireAlgorithmRandom(int nbRows, int nbColumns, BoardAbstract board) {
        super(nbRows, nbColumns, board);
    }

    @Override
    public Point getNextHit() {
        int x = -1;
        int y = -1;
        boolean coordinatesFound = false;

        do {
            x = ThreadLocalRandom.current().nextInt(0, this.nbRows);
            y = ThreadLocalRandom.current().nextInt(0, this.nbColumns);

            TileState state = board.checkState(x, y);
            if (state == TileState.UNKNOWN) {
                coordinatesFound = true;
            }
        } while (coordinatesFound == false);

        return new Point(x, y);
    }
}
