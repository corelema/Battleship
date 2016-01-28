package gameData;

import java.util.Objects;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Tile {
    public static final String OPEN_TILE = "open";
    public static final String SHIP_COVERED_TILE = "ship";
    public static final String FIRED_UPON_TILE = "fired";
    public static final String BATTLESHIP_HIT_TILE = "hit";

    private String tileState;
    private int x;
    private int y;

    public void setTileState(String state) {
        if (Objects.equals(state, OPEN_TILE) || (Objects.equals(state, SHIP_COVERED_TILE)) || (Objects.equals(state, FIRED_UPON_TILE))) {
            tileState = state;
        }
    }

    public Tile(int x, int y) {
        tileState = OPEN_TILE;
        x         = x;
        y         = y;
    }

    public boolean isOpen() {
        return Objects.equals(tileState, OPEN_TILE);
    }

    public boolean containsShip() {
        return Objects.equals(tileState, SHIP_COVERED_TILE);
    }

    public boolean hasBeenFiredUpon() {
        return Objects.equals(tileState, FIRED_UPON_TILE);
    }

    public boolean hasBeenHit() {
        return Objects.equals(tileState, BATTLESHIP_HIT_TILE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
