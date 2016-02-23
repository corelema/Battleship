package gameData;

/**
 * Created by paul.moon on 1/28/16.
 */
public class AttackResponse {
    //1st check to make
    private boolean coordinatesInBoundaries;

    //2nd check to make
    private boolean canAttack;

    //3rd check to make
    private boolean attackSuccessful;

    public AttackResponse(boolean coordinatesInBoundaries, boolean canAttack, boolean attackSuccessful) {
        this.coordinatesInBoundaries = coordinatesInBoundaries;
        this.canAttack = canAttack;
        this.attackSuccessful = attackSuccessful;
    }

    public boolean isCoordinatesInBoundaries() {
        return coordinatesInBoundaries;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public boolean isAttackSuccessful() {
        return attackSuccessful;
    }
}
