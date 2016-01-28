package gameData;

/**
 * Created by paul.moon on 1/28/16.
 */
public class AttackResponse {
    private boolean canAttack;
    private boolean attackSuccessful;

    public AttackResponse(boolean canAttack, boolean attackSuccessful) {
        this.canAttack = canAttack;
        this.attackSuccessful = attackSuccessful;
    }
}
