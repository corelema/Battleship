package gameData.Utils;

/**
 * Created by corentinl on 2/4/16.
 */
public class CoordinatesUtil {
    public static boolean coordinatesAreInBoud(int nbRows, int nbColumns, int x, int y) {
        return (x >= 0
        && x < nbRows
        && y >= 0
        && y < nbColumns);
    }

}
