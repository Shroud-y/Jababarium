package jababarium.content;

import arc.Core;
import arc.graphics.Color;
import arc.util.Log;
import jababarium.expand.units.UnitEntity.*;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class JBUnits {

    public static UnitType scout, zanuka, fray, blip;

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

        zanuka = UnitBuilder.create("zanuka")
                .flying()
                .health(500f)
                .speed(3.1f)
                .outlineRadius(0)
                .engine(2, 3f, Color.valueOf("6ec6ff"))
                .weapon(
                        WeaponBuilder.create("zanuka-gun")
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
        zanuka.constructor = UnitEntity::create;

        fray = UnitBuilder.create("fray")
                .flying()
                .health(600f)
                .speed(3f)
                .outlineRadius(0)
                .engine(2, 3f, Color.valueOf("6ec6ff"))
                .hitSize(6)
                .weapon(
                        WeaponBuilder.create("jababarium-fray-gun")
                                .reload(15f)
                                .rotate(true)
                                .mirror(true)
                                .top(true)
                                .pos(3f, 0f)
                                .bullet(new LaserBoltBulletType(6f, 10))
                                .build()
                )
                .build();
        fray.constructor = UnitEntity::create;

        Log.info("=== FRAY DEBUG ===");
        Log.info("Weapons count: @", fray.weapons.size);
        if(fray.weapons.size > 0){
            Weapon w = fray.weapons.first();
            Log.info("Weapon name: @", w.name);
            Log.info("Weapon x: @, y: @", w.x, w.y);
            Log.info("Weapon mirror: @", w.mirror);
            Log.info("Weapon rotate: @", w.rotate);
            Log.info("Weapon top: @", w.top);

            // Перевіряємо після load()
            Core.app.post(() -> {
                Log.info("After load - Region: @", w.region);
                Log.info("Region found: @", (w.region != null && w.region.found()));
                if (w.region != null) {
                    Log.info("Region name: @", w.region);
                }
            });
        }
    }
}
