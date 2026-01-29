package jababarium.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.type.StatusEffect;

public class JBStatus {
    public static StatusEffect ionizedStatus = new StatusEffect("ionized-status") {
        {
            color = Color.valueOf("72d4ff");
            damage = 2f;
            effect = Fx.chainLightning;
            speedMultiplier = 0.6f;
            reloadMultiplier = 0.8f;
        }
    };
}
