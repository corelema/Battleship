package gameData.enums;

/**
 * Created by corentinl on 1/24/16.
 */
public enum HitMiss {
    HIT("hit"),
    MISS("miss")
    ;

    private final String text;

    private HitMiss(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
