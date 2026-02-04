package jababarium.content;

import arc.graphics.Color;
import arc.math.Mathf;
// import arc.math.Angles;
// import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousFlameBulletType;
// import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Bullet;
// import arc.math.*;
// import arc.math.geom.*;
// import mindustry.entities;
// import mindustry.gen.*;

public class JBBullets {

    public static BulletType burst, singularityPoint, entropyBolt, transgression, chronosShell, chronosField;

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
                        if (b.time < 1f) {
                            Units.nearbyEnemies(b.team, b.x, b.y, splashDamageRadius, unit -> {
                                float angle = arc.math.Angles.angle(unit.x, unit.y, b.x, b.y);
                                float dst = arc.math.Mathf.dst(unit.x, unit.y, b.x, b.y);

                                float strength = dst * 0.12f;

                                strength = arc.math.Mathf.clamp(strength, 0f, 15f);

                                unit.vel.set(0, 0);

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

        entropyBolt = new BasicBulletType(8f, 150) {
            {
                lifetime = 40f;
                width = 12f;
                height = 12f;
                backColor = Color.valueOf("4fdfff");
                frontColor = Color.white;
                hitEffect = Fx.hitLancer;
                hitSound = JBSounds.shootGauss3;

                lightning = 3;
                lightningLength = 3;
                lightningColor = backColor;

                collidesAir = true;
                collidesGround = true;
                pierce = false;
            }

            @Override
            public void hit(Bullet b, float x, float y) {
                super.hit(b, x, y);

                if (b.damage > 15f) {
                    mindustry.gen.Unit target = mindustry.entities.Units.closestEnemy(b.team, x, y, 180f,
                            u -> u.dst(x, y) > 10f);

                    if (target != null) {
                        // Створюємо нову кулю
                        Bullet next = this.create(b.owner, b.team, x, y,
                                arc.math.Angles.angle(x, y, target.x, target.y));
                        next.damage = b.damage * 0.85f;

                        Fx.chainLightning.at(x, y, 0, Color.valueOf("4fdfff"), target);
                    }
                }
            }
        };

        transgression = new ContinuousFlameBulletType(300) {
            {
                shake = 3;
                hitColor = flareColor = lightColor = lightningColor = JBColor.lightSkyBack;
                colors = new Color[] { JBColor.lightSkyBack.cpy().mul(0.75f, 0.85f, 1f, 0.65f),
                        JBColor.lightSkyBack.cpy().mul(1f, 1f, 1f, 0.65f),
                        JBColor.lightSkyBack.cpy().lerp(JBColor.green, 0.5f), JBColor.green };
                width = 6;
                length = 380f;
                oscScl = 0.9f;
                oscMag *= 2f;
                lifetime = 35f;
                lightning = 4;
                lightningLength = 2;
                lightningLengthRand = 18;
                flareLength = 75;
                flareWidth = 6;
                hitEffect = JBFx.shootCircleSmall(JBColor.lightSkyBack);
                shootEffect = JBFx.lightningHitLarge(JBColor.lightSkyBack);
                lightningDamage = damage / 6f;
                despawnHit = false;
                pierceArmor = true;
                status = StatusEffects.burning;
            }

            @Override
            public void update(Bullet b) {
                super.update(b);

                if (Mathf.chanceDelta(0.11))
                    for (int i = 0; i < lightning; i++) {
                        Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y,
                                b.rotation() + Mathf.range(lightningCone / 2) + lightningAngle,
                                lightningLength + Mathf.random(lightningLengthRand));
                    }
            }

            @Override
            public void hit(Bullet b, float x, float y) {
                hitEffect.at(x, y, b.rotation(), hitColor);
                hitSound.at(x, y, hitSoundPitch, hitSoundVolume);

                Effect.shake(hitShake, hitShake, b);

                Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, x, y,
                        b.rotation() + Mathf.range(lightningCone / 2) + lightningAngle,
                        lightningLength + Mathf.random(lightningLengthRand));
            }
        };

        chronosField = new BasicBulletType(0f, 0f) {
            {
                lifetime = 60f * 5f;
                collides = false;
                collidesTiles = false;
                collidesAir = false;
                absorbable = false;
                pierce = true;
                hitEffect = Fx.none;
                despawnEffect = Fx.none;
            }

            @Override
            public void update(Bullet b) {
                super.update(b);

                if (b.timer.get(1, 5f)) {
                    float remaining = Math.max(0f, b.lifetime - b.time);
                    float applyDuration = Math.min(10f, remaining);
                    Units.nearbyEnemies(b.team, b.x, b.y, 240f, unit -> {
                        unit.apply(JBStatus.chronosStop, applyDuration);
                    });
                }
            }
        };

        chronosShell = new ArtilleryBulletType(6f, 0f) {
            {
                lifetime = 200f;
                width = 16f;
                height = 20f;
                shrinkY = 0.3f;
                backColor = Color.valueOf("7fd6ff");
                frontColor = Color.white;
                trailWidth = 3.5f;
                trailLength = 30;
                trailColor = backColor;

                hitEffect = Fx.instBomb;
                despawnEffect = Fx.instBomb;
                hitSound = JBSounds.largeBeam;
            }

            @Override
            public void hit(Bullet b, float x, float y) {
                super.hit(b, x, y);
                chronosField.create(b.owner, b.team, x, y, 0f);
            }
        };
    }
}
