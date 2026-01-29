package jababarium.util.func;

import arc.func.Boolf;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.world.Tile;

import static mindustry.Vars.world;

public class JBFunc {
    public static final Rand rand = new Rand(0);
    private static Tile tileParma;
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

    public static Seq<Boolf<Tile>> formats() {
        Seq<Boolf<Tile>> seq = new Seq<>(3);

        seq.add(
                tile -> world.getQuadBounds(Tmp.r1).contains(tile.getBounds(Tmp.r2)),
                tile -> tile.floor().isLiquid && !tile.cblock().solid && !tile.floor().solid && !tile.overlay().solid && !tile.block().solidifes,
                tile -> !tile.floor().isDeep() && !tile.cblock().solid && !tile.floor().solid && !tile.overlay().solid && !tile.block().solidifes
        );

        return seq;
    }

    public static Seq<Tile> getAcceptableTiles(int x, int y, int range, Boolf<Tile> bool) {
        Seq<Tile> tiles = new Seq<>(true, (int) (Mathf.pow(range, 2) * Mathf.pi), Tile.class);
        Geometry.circle(x, y, range, (x1, y1) -> {
            if ((tileParma = world.tile(x1, y1)) != null && bool.get(tileParma)) {
                tiles.add(world.tile(x1, y1));
            }
        });
        return tiles;
    }
}
