package jababarium.util.func;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import jababarium.content.JBFx;

public class JBFunc {
    public static final Rand rand = new Rand(0);
    private static final Vec2 vec21 = new Vec2(),
            vec22 = new Vec2(),
            vec23 = new Vec2();

    public static Rand rand(long id) {
        rand.setSeed(id);
        return rand;
    }

    public static void randFadeLightningEffect(float x, float y, float range, float lightningPieceLength, Color color,
            boolean in) {
        randFadeLightningEffectScl(x, y, range, 0.55f, 1.1f, lightningPieceLength, color, in);
    }

    public static void randFadeLightningEffectScl(float x, float y, float range, float sclMin, float sclMax,
            float lightningPieceLength, Color color, boolean in) {
        vec21.rnd(range).scl(Mathf.random(sclMin, sclMax)).add(x, y);
        // (in ? JBFx.chainLightningFadeReversed : JBFx.chainLightningFade).at(x, y,
        // lightningPieceLength, color,
        // vec21.cpy());
    }
}
