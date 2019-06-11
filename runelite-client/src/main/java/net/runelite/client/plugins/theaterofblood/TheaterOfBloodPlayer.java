package net.runelite.client.plugins.theaterofblood;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Actor;

public class TheaterOfBloodPlayer {
    @Getter
    private String name;

    @Getter @Setter
    private int ticksRemaining;

    @Getter @Setter
    private Actor target;

    @Getter
    private float damageDone;

    public TheaterOfBloodPlayer(String name) {
        this.name = name;
        ticksRemaining = 0;
        damageDone = 0;
    }

    public int advanceTick() {
        if (ticksRemaining < 0) {
            target = null;
        } else {
            --ticksRemaining;
        }
        return ticksRemaining;
    }

    public float addDamageDone(float damage) {
        damageDone += damage;
        return damageDone;
    }


}
