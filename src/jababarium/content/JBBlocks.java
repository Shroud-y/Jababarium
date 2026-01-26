package jababarium.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import jababarium.expand.block.special.FluxReactor;
import jababarium.expand.block.special.SelfHealingLiquidBlocks;
import jababarium.util.graphic.DrawFunc;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.pattern.ShootPattern;
import mindustry.graphics.Drawf;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.gen.Bullet;
import jababarium.util.func.JBFunc;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.part.DrawPart.PartProgress;
import mindustry.entities.part.RegionPart;
import jababarium.expand.block.commandable.BombLauncher;
import jababarium.expand.bullets.LightningLinkerBulletType;
import jababarium.util.graphic.OptionalMultiEffect;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;
// import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static arc.graphics.g2d.Lines.lineAngle;
import static mindustry.type.ItemStack.with;

public class JBBlocks {

    public static Block manualArtillery, cryostalConveyor, cryostalRouter, cryostalJunction, cryostalBridge,
            fluxReactor, helix, selfhealingConduit, singularityNeedle, selfhealingJunction, selfhealingRouter,
            entropyChain;

    public static void load() {

        manualArtillery = new BombLauncher("tochka-u") {
            {
                size = 3;
                storage = 4;

                requirements(
                        Category.defense,
                        with(
                                Items.copper, 300,
                                Items.lead, 200,
                                Items.silicon, 150,
                                Items.surgeAlloy, 100));

                bullet = new LightningLinkerBulletType(0f, 200) {
                    {
                        trailWidth = 4.5f;
                        trailLength = 66;

                        spreadEffect = slopeEffect = Fx.none;
                        trailEffect = JBFx.hitSparkHuge;
                        trailInterval = 5;

                        backColor = trailColor = hitColor = lightColor = lightningColor = JBColor.thurmixRed;
                        frontColor = JBColor.thurmixRed;
                        randomGenerateRange = 240f;
                        randomLightningNum = 1;
                        linkRange = 120f;
                        range = 200f;

                        drawSize = 20f;

                        drag = 0.0035f;
                        fragLifeMin = 0.3f;
                        fragLifeMax = 1f;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = 1.25f;
                        fragBullets = 3;
                        fragBullet = new FlakBulletType(3.75f, 50) {
                            {
                                trailColor = lightColor = lightningColor = JBColor.thurmixRed;
                                backColor = JBColor.thurmixRed;
                                frontColor = JBColor.thurmixRed;

                                trailLength = 14;
                                trailWidth = 2.7f;
                                trailRotation = true;
                                trailInterval = 3;

                                trailEffect = JBFx.polyTrail(backColor, frontColor, 4.65f, 22f);
                                trailChance = 0f;
                                knockback = 12f;
                                lifetime = 40f;
                                width = 17f;
                                height = 42f;
                                collidesTiles = false;
                                splashDamageRadius = 60f;
                                splashDamage = damage * 0.6f;
                                lightning = 3;
                                lightningLength = 8;
                                smokeEffect = Fx.shootBigSmoke2;
                                hitShake = 8f;
                                // hitSound = Sounds.plasmaboom;
                                status = StatusEffects.sapped;

                                statusDuration = 60f * 10;
                            }
                        };
                        // hitSound = Sounds.explosionbig;
                        splashDamageRadius = 120f;
                        splashDamage = 200;
                        lightningDamage = 40f;

                        collidesTiles = true;
                        pierce = false;
                        collides = false;
                        lifetime = 10;
                        despawnEffect = new OptionalMultiEffect(
                                JBFx.crossBlast(hitColor, splashDamageRadius * 0.8f),
                                JBFx.blast(hitColor, splashDamageRadius * 0.8f),
                                JBFx.circleOut(hitColor, splashDamageRadius * 0.8f));
                    }

                    @Override
                    public void update(Bullet b) {
                        super.update(b);

                        for (int j = 0; j < 2; j++) {
                            JBFunc.randFadeLightningEffect(b.x, b.y, Mathf.random(360), Mathf.random(7, 12),
                                    backColor, Mathf.chance(0.5));
                        }
                        ;
                    }
                };

                reloadTime = 300f;

                consumePowerCond(26f, BombLauncherBuild::isCharging);
                consumeItem(Items.surgeAlloy, 2);
                itemCapacity = 16;
                health = 1200;
            }
        };

        cryostalBridge = new ItemBridge("cryostal-item-bridge") {
            {
                requirements(Category.distribution, with(
                        JBItems.cryostal, 3,
                        JBItems.adamantium, 3));
                health = 15;
                range = 8;
                canOverdrive = true;
                itemCapacity = 14;
                transportTime = 5f;
            }
        };

        cryostalJunction = new Junction("cryostal-junction") {
            {
                requirements(Category.distribution, with(
                        JBItems.cryostal, 2,
                        JBItems.adamantium, 3));
                health = 10;
                itemCapacity = 20;
            }
        };

        cryostalConveyor = new Conveyor("cryostal-conveyor") {
            {
                requirements(Category.distribution, with(
                        JBItems.cryostal, 1,
                        JBItems.adamantium, 1));

                speed = 0.1f;
                displayedSpeed = 16;
                itemCapacity = 14;
                health = 10;
                canOverdrive = true;
                junctionReplacement = cryostalJunction;
                bridgeReplacement = cryostalBridge;
            }
        };

        cryostalRouter = new Router("cryostal-router") {
            {
                requirements(Category.distribution, with(
                        JBItems.cryostal, 2,
                        JBItems.adamantium, 2));
                health = 15;
                canOverdrive = false;
                speed = 0f;
                itemCapacity = 20;
            }
        };

        fluxReactor = new FluxReactor("flux-reactor") {
            {
                requirements(Category.power, with(
                        JBItems.amalgam, 3000,
                        JBItems.sergium, 3500,
                        JBItems.pulsarite, 3000));
                size = 9;
                coolant = JBLiquids.argon;
                ambientSound = JBSounds.fluxReactorWorking;

                consumeLiquid(JBLiquids.argon, 2f);
                consumeItems(with(JBItems.sergium, 2, JBItems.pulsarite, 3, JBItems.adamantium, 4));

            }
        };

        helix = new ItemTurret("helix") {
            {
                armor = 30;
                size = 5;
                outlineRadius = 7;
                range = 700;
                heatColor = JBColor.green;
                // unitSort = NHUnitSorts.regionalHPMaximum_All;

                coolant = new ConsumeLiquid(Liquids.cryofluid, 1f);
                liquidCapacity = 120;
                coolantMultiplier = 2.5f;

                buildCostMultiplier *= 2;
                canOverdrive = false;

                shoot = new ShootPattern();
                inaccuracy = 0;

                ammoPerShot = 40;
                coolantMultiplier = 0.8f;
                rotateSpeed = 1f;

                float chargeCircleFrontRad = 12f;

                shootEffect = new Effect(60f, 500f, e -> {
                    float scl = 0.05f;
                    if (e.data instanceof Float)
                        scl *= (float) e.data;
                    Draw.color(heatColor, Color.white, e.fout() * 0.25f);

                    float rand = Mathf.randomSeed(e.id, 60f);
                    float extend = Mathf.curve(e.fin(Interp.pow10Out), 0.075f, 1f) * scl;
                    float rot = e.fout(Interp.pow10In);

                    for (int i : Mathf.signs) {
                        DrawFunc.tri(e.x, e.y, chargeCircleFrontRad * 1.2f * e.foutpowdown() * scl, 200 + 500 * extend,
                                e.rotation + (90 + rand) * rot + 90 * i - 45);
                    }

                    for (int i : Mathf.signs) {
                        DrawFunc.tri(e.x, e.y, chargeCircleFrontRad * 1.2f * e.foutpowdown() * scl, 200 + 500 * extend,
                                e.rotation + (90 + rand) * rot + 90 * i + 45);
                    }
                });

                smokeEffect = new Effect(50, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(e.fout() * 5f);
                    Lines.circle(e.x, e.y, e.fin() * 300);
                    Lines.stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, e.fin() * 180);
                    Lines.stroke(e.fout() * 3.2f);
                    Angles.randLenVectors(e.id, 30, 18 + 80 * e.fin(), (x, y) -> {
                        lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 14 + 5);
                    });
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fout() * 120, heatColor, 0.7f);
                });

                recoil = 18f;
                // shootSound = Sounds.laserblast;
                health = 3000;
                shootCone = 5f;
                maxAmmo = 80;
                consumePowerCond(800f, TurretBuild::isActive);
                reload = 90f;

                ammo(Items.plastanium, JBBullets.burst);
                // ammo(
                // Items.copper, new BasicBulletType(3f, 20) {
                // {
                // width = 100f;
                // height = 100f;
                // lifetime = 700f;
                // }
                // });

                requirements(Category.turret, BuildVisibility.shown,
                        with(JBItems.cryostal, 300, JBItems.surgeAlloy, 425, JBItems.plastanium, 300));

            }
        };

        selfhealingRouter = new SelfHealingLiquidBlocks.SelfHealingRouter("self-healing-router") {
            {
                requirements(Category.liquid, ItemStack.with(
                        JBItems.cryostal, 5,
                        JBItems.metaglass, 10,
                        JBItems.adamantium, 7));
            }
        };

        selfhealingJunction = new SelfHealingLiquidBlocks.SelfHealingJunction("self-healing-junction") {
            {
                requirements(Category.liquid, ItemStack.with(
                        JBItems.cryostal, 5,
                        JBItems.adamantium, 6,
                        JBItems.metaglass, 9));
                health = 30;
            }
        };

        selfhealingConduit = new SelfHealingLiquidBlocks.SelfHealingConduit("self-healing-conduit") {
            {
                requirements(Category.liquid, ItemStack.with(
                        JBItems.cryostal, 4,
                        JBItems.adamantium, 5,
                        JBItems.metaglass, 7));
                health = 30;
                junctionReplacement = selfhealingJunction;
            }
        };

        singularityNeedle = new ItemTurret("singularity-needle") {
            {
                requirements(Category.turret, with(
                        Items.silicon, 450,
                        JBItems.sergium, 300,
                        Items.phaseFabric, 200,
                        Items.plastanium, 150));

                size = 6;
                health = 2400;
                range = 380f;
                reload = 180f;
                recoil = 2f;
                shake = 2f;

                ammo(
                        Items.phaseFabric, JBBullets.singularityPoint);

                consumePower(12f);
                consumeLiquid(Liquids.cryofluid, 0.5f);

                drawer = new DrawTurret("singularity-needle") {
                    {
                        parts.add(new RegionPart("-part") {
                            {
                                progress = PartProgress.warmup;
                                mirror = true;
                                under = false;

                                moveX = 4f;
                                moveY = -2f;
                                moveRot = -15f;

                                moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                            }
                        });

                        parts.add(new RegionPart("-glow") {
                            {
                                blending = Blending.additive;
                                color = Color.valueOf("bf92f9");
                                outline = false;
                                mirror = false;
                                progress = PartProgress.warmup;
                                colorTo = Color.white;
                            }
                        });
                    }
                };
            }
        };
        entropyChain = new ItemTurret("entropy-chain") {
            {
                requirements(Category.turret, with(
                        Items.titanium, 200,
                        Items.plastanium, 150,
                        Items.silicon, 200,
                        JBItems.sergium, 80));

                size = 3;
                health = 1400;
                range = 260f;
                reload = 40f;
                inaccuracy = 5f;

                consumePower(6f);

                ammo(
                        Items.plastanium, JBBullets.entropyBolt,

                        Items.phaseFabric, new BasicBulletType(10f, 200) {
                            {
                                this.hitEffect = Fx.hitLancer;
                                this.backColor = Color.valueOf("4fdfff");
                            }
                        });

                drawer = new DrawTurret("entropy-chain") {
                    {

                        parts.add(new RegionPart("-spinner") {
                            {
                                progress = PartProgress.warmup;
                                // spin = 6f;
                                mirror = false;
                                under = true;
                            }
                        });

                        parts.add(new RegionPart("-barrel") {
                            {
                                progress = PartProgress.recoil;
                                moveY = -4f;
                                mirror = false;
                            }
                        });

                        parts.add(new RegionPart("-glow") {
                            {
                                blending = Blending.additive;
                                color = Color.valueOf("4fdfff");
                                outline = false;
                                mirror = false;
                            }
                        });
                    }
                };
            }
        };

    }
}