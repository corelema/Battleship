package gameData.fireAlgorithms;

import gameData.Boards.BoardAbstract;

import java.awt.*;

/**
 * Created by corentinl on 2/2/16.
 */
public abstract class FireAlgorithmAbstract {
    protected int nbRows;
    protected int nbColumns;
    protected BoardAbstract board;

    public FireAlgorithmAbstract(int nbRows, int nbColumns, BoardAbstract board) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.board = board;
    }

    public abstract Point getNextHit();
}
