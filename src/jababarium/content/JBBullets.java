package jababarium.content;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Bullet;

public class JBBullets {

    public static BulletType burst, singularityPoint;

    public static void load() {

        // Твій попередній зелений снаряд
        burst = new ArtilleryBulletType(16f, 250) {
            {
                lifetime = 80f;
                width = 14f;
                height = 24f;
                shrinkY = 0.3f;
                backColor = Color.valueOf("#5CE65C");
                frontColor = Color.white;
                lightColor = backColor;
                trailWidth = 4.5f;
                trailLength = 25;
                trailColor = backColor;
                hitSound = JBSounds.shootGauss1;
                shootEffect = Fx.shootBigColor;
                hitEffect = Fx.massiveExplosion;
                despawnEffect = Fx.scatheExplosion;

                fragBullets = 12;
                fragBullet = new BasicBulletType(5f, 40) {
                    {
                        width = 7f;
                        height = 12f;
                        lifetime = 25f; // Трохи збільшив, щоб було видно розліт
                        backColor = Color.valueOf("#5CE65C");
                        frontColor = Color.white;
                        lightning = 2;
                        lightningColor = backColor;
                    }
                };
            }
        };

        singularityPoint = new PointBulletType() {
            {
                shootEffect = Fx.instShoot;
                hitEffect = Fx.instHit;
                smokeEffect = Fx.smokeCloud;
                trailEffect = Fx.instTrail;

                // Фіолетовий лазер
                trailColor = Color.valueOf("bf92f9");
                lightColor = Color.valueOf("bf92f9");

                hitSound = JBSounds.shootGauss3;
                damage = 3050f;
                speed = 500f;
                hitShake = 8f;

                fragOnHit = true;
                fragBullets = 1;

                fragBullet = new BasicBulletType(0f, 0) {
                    {
                        lifetime = 15f;
                        splashDamageRadius = 400f;
                        splashDamage = 800f;

                        hitEffect = JBFx.singularityCollapse;
                        despawnEffect = Fx.none;

                        collidesAir = true;
                        collidesGround = true;
                    }

                    @Override
                    public void update(Bullet b) {
                        // Виконуємо притягування в перші кадри життя (поки діє колапс)
                        if (b.time < 1f) {
                            Units.nearbyEnemies(b.team, b.x, b.y, splashDamageRadius, unit -> {
                                float angle = arc.math.Angles.angle(unit.x, unit.y, b.x, b.y);
                                float dst = arc.math.Mathf.dst(unit.x, unit.y, b.x, b.y);

                                // Розраховуємо силу так, щоб вона була достатньою, але не надмірною
                                // 0.12f - це коефіцієнт "м'якості" притягування.
                                // Якщо все ще перелітають - зменш до 0.08f.
                                float strength = dst * 0.12f;

                                // Обмежуємо максимальну швидкість, щоб юніти не "вилітали" з карти
                                strength = arc.math.Mathf.clamp(strength, 0f, 15f);

                                // ПЕРЕШКОДЖАЄМО ПЕРЕЛЬОТУ:
                                // 1. Спочатку повністю зупиняємо юніта
                                unit.vel.set(0, 0);

                                // 2. Надаємо йому новий вектор прямо до центру
                                unit.vel.add(
                                        arc.math.Mathf.cosDeg(angle) * strength,
                                        arc.math.Mathf.sinDeg(angle) * strength);
                            });

                            JBFx.singularityCollapse.at(b.x, b.y);
                        }
                        super.update(b);
                    }
                };
            }
        };
    }
}