package jababarium.content;

import arc.graphics.Color;
import jababarium.expand.units.UnitEntity.*;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class JBUnits {

    public static UnitType scout;

    public static void load(){

        scout = UnitBuilder.create("scout")
                .flying()
                .health(420f)
                .speed(2.8f)
                .outlineRadius(0)
                .engine(1, 3f, Color.valueOf("6ec6ff"))
                .weapon(
                        WeaponBuilder.create("scout-gun")
                                .reload(30f)
                                .bullet(new BasicBulletType(4f, 20){{
                                    width = 6f;
                                    height = 8f;
                                    lifetime = 40f;
                                    hitEffect = Fx.hitBulletSmall;
                                    despawnEffect = Fx.none;
                                }})
                                .range(160f)
                                .build()
                )
                .build();
        scout.constructor = UnitEntity::create;
    }

}
