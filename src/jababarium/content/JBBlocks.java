//TODO Finish turrets visual and balance

package jababarium.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import jababarium.expand.block.power.EffectPowerGenerator;
import jababarium.expand.block.special.AntiMatterWarper;
import mindustry.type.Item;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.draw.*;
import arc.math.Mathf;
import arc.util.Time;
import jababarium.util.graphic.DrawFunc;
import jababarium.expand.block.special.FluxReactor;
import jababarium.expand.block.special.SelfHealingLiquidBlocks;
import jababarium.util.graphic.DrawFunc;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.gen.Bullet;
import jababarium.util.func.JBFunc;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.RailBulletType;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.DrawPart.PartProgress;
import mindustry.entities.part.RegionPart;
import jababarium.expand.block.commandable.BombLauncher;
import jababarium.expand.bullets.LightningLinkerBulletType;
import jababarium.util.graphic.OptionalMultiEffect;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.production.Drill;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.meta.BuildVisibility;

import javax.swing.plaf.ColorUIResource;

import static arc.graphics.g2d.Lines.lineAngle;
import static mindustry.type.ItemStack.with;

public class JBBlocks {

    public static Block manualArtillery, cryostalConveyor, cryostalRouter, cryostalJunction, cryostalBridge,
            fluxReactor, helix, selfhealingConduit, singularityNeedle, selfhealingJunction, selfhealingRouter,
            entropyChain, cryostalDrill, selfhealingliquidBridge, ionizer, solarApex, chronos, antiMatterWarper, ignis,
            hastae,
            adamantiumSynthesizer, overlord, transgression, apex;

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

        selfhealingliquidBridge = new SelfHealingLiquidBlocks.SelfHealingLiquidBridge("self-healing-liquid-bridge") {
            {
                requirements(Category.liquid, ItemStack.with(
                        JBItems.cryostal, 5,
                        JBItems.adamantium, 5,
                        JBItems.metaglass, 5));
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
                bridgeReplacement = selfhealingliquidBridge;
            }
        };

        entropyChain = new ItemTurret("entropy-chain") {
            {
                requirements(Category.turret, with(
                        Items.titanium, 200,
                        Items.plastanium, 150,
                        Items.silicon, 200,
                        JBItems.feronium, 200));

                size = 3;
                health = 1400;
                range = 260f;
                reload = 40f;
                inaccuracy = 5f;

                consumePower(6f);

                ammo(
                        Items.plastanium, JBBullets.entropyBolt);
            }
        };

        ignis = new ItemTurret("ignis") {
            {
                requirements(Category.turret,
                        with(Items.graphite, 220, JBItems.feronium, 200, Items.silicon, 120, JBItems.cryostal, 100));

                size = 5;
                health = 1350;
                reload = 50f;
                range = 350f;
                recoil = 2f;
                rotateSpeed = 6f;
                inaccuracy = 3f;
                shootCone = 20f;
                ammoUseEffect = Fx.casing2;

                heatColor = JBColor.thurmixRed;

                shoot = new ShootAlternate(8f) {
                    {
                        shots = 6;
                        shotDelay = 4f;
                    }
                };

                ammo(
                        Items.graphite, new BasicBulletType(7f, 95) {
                            {
                                width = 9f;
                                height = 12f;
                                lifetime = 50f;
                                ammoMultiplier = 4;
                                status = StatusEffects.corroded;
                                statusDuration = 150f;
                                frontColor = Color.valueOf("ffaa5f");
                                backColor = Color.valueOf("d37f40");
                                trailWidth = 1.5f;
                                trailLength = 6;
                                trailColor = Color.valueOf("d37f40");
                            }
                        },
                        Items.silicon, new BasicBulletType(6f, 50) {
                            {
                                width = 7f;
                                height = 10f;
                                lifetime = 58f;
                                homingPower = 0.08f;
                                homingRange = 80f;
                                ammoMultiplier = 5;
                                frontColor = Color.valueOf("bf92f9");
                                backColor = Color.valueOf("665c9f");
                            }
                        },
                        Items.pyratite, new BasicBulletType(6f, 105) {
                            {
                                width = 10f;
                                height = 14f;
                                lifetime = 58f;
                                status = StatusEffects.melting;
                                statusDuration = 240f;
                                makeFire = true;
                                frontColor = Color.orange;
                                backColor = Color.valueOf("d37f40");
                                trailEffect = Fx.incendTrail;
                                hitEffect = Fx.fireHit;
                            }
                        });

                smokeEffect = new Effect(50, e -> {
                    Draw.color(heatColor);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fout() * 120, heatColor, 0.7f);
                });
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

        singularityNeedle = new ItemTurret("singularity-needle") {
            {
                requirements(Category.turret, with(
                        JBItems.cryostal, 200,
                        JBItems.surgeAlloy, 300,
                        JBItems.chronite, 200,
                        Items.plastanium, 300));

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
                heatColor = Color.valueOf("#FFC900");

            }
        };

        cryostalDrill = new Drill("cryostal-drill") {
            {

                requirements(Category.production, ItemStack.with(
                        JBItems.feronium, 200,
                        JBItems.cryostal, 150,
                        JBItems.plastanium, 300));

                size = 4;
                health = 200;
                drillTime = 30f;
                itemCapacity = 30;
                heatColor = Color.valueOf("bf92f9");
                tier = 8;

                consumePower(6f);

                updateEffect = JBFx.polyTrail(Color.valueOf("#54D1CC"), Color.valueOf("#1479A8"), 5f, 60f);

                updateEffectChance = 0.06f;
                drawMineItem = true;
                ambientSound = Sounds.loopDrill;
                ambientSoundVolume = 0.05f;

            }
        };

        overlord = new PowerTurret("overlord") {
            {
                requirements(Category.turret, with(
                        Items.surgeAlloy, 600,
                        Items.plastanium, 450,
                        Items.phaseFabric, 350,
                        Items.silicon, 800,
                        JBItems.adamantium, 400));

                size = 5;
                health = 3800;
                range = 500f;
                reload = 720f;
                recoil = 8f;
                recoilTime = 180f;
                shake = 5f;

                consumePower(30f);
                consumeCoolant(1f);

                heatColor = Color.valueOf("bf92f9");

                shootSound = JBSounds.blastShockwave;
                chargeSound = JBSounds.blastShockwave;

                shoot = new ShootPattern() {
                    {
                        firstShotDelay = 120f;
                    }
                };

                smokeEffect = new Effect(50, e -> {
                    Draw.color(heatColor);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fout() * 120, heatColor, 0.7f);
                });

                ammoUseEffect = new Effect(120f, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(Mathf.curve(e.fin(), 0, 1) * 4f);
                    Lines.circle(e.x, e.y, e.fout() * 100f);

                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fin() * 150f, heatColor, 0.8f);

                    Angles.randLenVectors(e.id, 40, 20f + e.fin() * 100f, (x, y) -> {
                        Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 1f);
                    });
                });

                shootEffect = new Effect(60f, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(e.fout() * 5f);
                    Lines.circle(e.x, e.y, e.fin() * 130f);
                    Drawf.light(e.x, e.y, e.fout() * 200f, heatColor, 0.9f);
                    Fx.massiveExplosion.at(e.x, e.y, heatColor);
                });

                shootType = new BasicBulletType(3.5f, 600f) {
                    {
                        width = 45f;
                        height = 45f;
                        shrinkX = 0f;
                        shrinkY = 0f;
                        spin = 1f;
                        lifetime = 160f;

                        pierce = true;
                        pierceCap = 3;
                        absorbable = false;

                        frontColor = Color.white;
                        backColor = Color.valueOf("bf92f9");
                        trailColor = Color.valueOf("995dff");
                        trailWidth = 18f;
                        trailLength = 80;

                        hitEffect = Fx.impactReactorExplosion;
                        despawnEffect = Fx.impactReactorExplosion;
                        hitSound = JBSounds.blastShockwave;

                        lightningColor = Color.valueOf("bf92f9");
                        lightningDamage = 90f;
                        lightningLength = 25;
                        lightningLengthRand = 15;
                    }

                    @Override
                    public void update(Bullet b) {
                        super.update(b);

                        if (b.timer.get(1, 5f)) {
                            Fx.sparkShoot.at(b.x, b.y, b.rotation() + 90, lightningColor);
                            Lightning.create(b.team, lightningColor, lightningDamage, b.x, b.y, b.rotation() + 90,
                                    lightningLength + Mathf.range(lightningLengthRand));

                            Fx.sparkShoot.at(b.x, b.y, b.rotation() - 90, lightningColor);
                            Lightning.create(b.team, lightningColor, lightningDamage, b.x, b.y, b.rotation() - 90,
                                    lightningLength + Mathf.range(lightningLengthRand));
                        }
                    }
                };
            }
        };

        transgression = new PowerTurret("transgression") {
            {
                requirements(Category.turret, with(
                        Items.silicon, 1200,
                        Items.plastanium, 500,
                        Items.phaseFabric, 400,
                        JBItems.adamantium, 250));

                size = 5;
                health = 3500;
                range = 400f;

                consumePower(25f);
                consumeLiquid(JBLiquids.nectron, 0.4f);

                shootSound = JBSounds.beam;
                loopSound = JBSounds.bioLoop;
                loopSoundVolume = 1.5f;

                shootType = JBBullets.transgression;

                heatColor = Color.valueOf("84f5d9");

                smokeEffect = new Effect(50, e -> {
                    Draw.color(heatColor);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fout() * 120, heatColor, 0.7f);
                });

                buildType = () -> new PowerTurretBuild() {
                    public float internalHeat = 0f;
                    public boolean isLocked = false;

                    @Override
                    public void updateTile() {
                        if (isLocked) {
                            internalHeat = Mathf.approach(internalHeat, 0f, 0.003f * Time.delta);
                            if (internalHeat <= 0.01f) {
                                isLocked = false;
                            }
                        }

                        if (isShooting() && !isLocked && canConsume()) {
                            internalHeat = Mathf.approach(internalHeat, 1.2f, 0.005f * Time.delta);
                            if (internalHeat >= 1f) {
                                isLocked = true;
                            }
                        } else if (!isShooting() || isLocked) {
                            internalHeat = Mathf.approach(internalHeat, 0f, 0.002f * Time.delta);
                        }

                        super.updateTile();
                    }

                    @Override
                    public boolean canConsume() {
                        return super.canConsume() && !isLocked;
                    }

                    @Override
                    public boolean shouldConsume() {
                        return isShooting() && !isLocked;
                    }

                    @Override
                    public void draw() {
                        super.draw();
                        if (internalHeat > 0.1f) {
                            Draw.z(Layer.effect);
                            Draw.color(Color.orange, Color.red, Mathf.clamp(internalHeat - 0.5f) * 2f);
                            Draw.alpha(internalHeat * 0.4f);
                            Fill.rect(x, y, size * 7f, size * 7f);
                            Draw.reset();
                        }
                    }
                };
            }
        };

        ionizer = new PowerTurret("ionizer") {
            {
                requirements(Category.turret, with(
                        JBItems.pulsarite, 200,
                        Items.silicon, 950,
                        Items.plastanium, 600,
                        JBItems.chronite, 400,
                        JBItems.cryostal, 800));

                health = 4600;
                size = 11;
                range = 600f;
                reload = 40f;
                recoil = 3f;
                shake = 2f;
                shootSound = JBSounds.shootGauss1;
                heatColor = Color.valueOf("72d4ff");

                consumePower(35f);
                // coolant = new ConsumeLiquid(Liquids.cryofluid, 1f);

                shoot = new ShootBarrel() {
                    {
                        barrels = new float[] {
                                -25f, 4f, 0f,
                                25f, 4f, 0f
                        };
                        shots = 2;
                        shotDelay = 5f;
                    }
                };

                shootType = new BasicBulletType(16f, 1200f) {
                    {
                        width = 50;
                        height = 24f;
                        lifetime = 35f;

                        homingPower = 0.08f;
                        homingRange = 50f;
                        homingDelay = 5f;

                        frontColor = Color.white;
                        backColor = Color.valueOf("72d4ff");
                        trailColor = Color.valueOf("72d4ff");
                        trailWidth = 3f;
                        trailLength = 20;

                        status = JBStatus.ionizedStatus;
                        statusDuration = 180f;

                        hitEffect = Fx.massiveExplosion;
                        despawnEffect = Fx.bigShockwave;
                    }
                };

                smokeEffect = new Effect(30, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(e.fout() * 5f);
                    Lines.circle(e.x, e.y, e.fin() * 50);
                    Lines.stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, e.fin() * 30);
                    Lines.stroke(e.fout() * 3.2f);
                    Angles.randLenVectors(e.id, 30, 18 + 80 * e.fin(), (x, y) -> {
                        lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 14 + 5);
                    });
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fout() * 120, heatColor, 0.7f);
                });
            }
        };

        solarApex = new PowerTurret("solar-apex") {
            {
                requirements(Category.turret, with(
                        JBItems.singularium, 1200,
                        JBItems.pulsarite, 1400,
                        JBItems.sergium, 1600,
                        Items.surgeAlloy, 1200,
                        Items.phaseFabric, 900));

                size = 8;
                health = 9000;
                range = 1200f;
                reload = 240f;
                recoil = 10f;
                recoilTime = 120f;
                shake = 8f;
                rotateSpeed = 0.6f;
                shootCone = 2f;

                consumePower(120f);
                consumeLiquid(Liquids.cryofluid, 5f);
                coolant = new ConsumeLiquid(Liquids.cryofluid, 2.5f);
                liquidCapacity = 480f;
                coolantMultiplier = 3.5f;

                heatColor = Color.valueOf("ffd36b");
                shootSound = JBSounds.largeBeam;
                loopSound = JBSounds.bioLoop;
                loopSoundVolume = 2f;

                shoot = new ShootPattern() {
                    {
                        firstShotDelay = 90f;
                    }
                };

                drawer = new DrawTurret() {
                    {
                        parts.add(new RegionPart("-glow") {
                            {
                                blending = Blending.additive;
                                color = heatColor;
                                progress = PartProgress.warmup;
                                outline = false;
                            }
                        });
                    }
                };

                shootEffect = new Effect(70f, e -> {
                    Draw.color(heatColor, Color.white, e.fin());
                    Lines.stroke(e.fout() * 7f);
                    Lines.circle(e.x, e.y, e.fin() * 200f);
                    Drawf.light(e.x, e.y, e.fin() * 260f, heatColor, 0.9f);
                });

                ammoUseEffect = new Effect(140f, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(Mathf.curve(e.fin(), 0, 1) * 5f);
                    Lines.circle(e.x, e.y, e.fout() * 180f);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fin() * 240f, heatColor, 0.9f);
                });

                shootType = new ContinuousLaserBulletType(4200f) {
                    {
                        length = 1200f;
                        width = 26f;
                        lifetime = 60f;
                        hitSize = 20f;
                        drawSize = 420f;
                        knockback = 2.5f;
                        pierceArmor = true;

                        hitColor = lightColor = lightningColor = heatColor;
                        colors = new Color[] {
                                heatColor.cpy().mul(1f, 0.85f, 0.6f, 0.6f),
                                heatColor.cpy().mul(1f, 0.9f, 0.7f, 0.8f),
                                Color.white
                        };

                        hitEffect = Fx.impactReactorExplosion;
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.smokeCloud;
                        despawnEffect = Fx.none;

                        status = StatusEffects.melting;
                        statusDuration = 60f * 6f;
                    }
                };
            }
        };

        chronos = new PowerTurret("chronos") {
            {
                requirements(Category.turret, with(
                        JBItems.chronite, 1200,
                        JBItems.singularium, 900,
                        JBItems.pulsarite, 1000,
                        Items.surgeAlloy, 900,
                        Items.phaseFabric, 700));

                size = 8;
                health = 8200;
                range = 1000f;
                reload = 360f;
                recoil = 6f;
                recoilTime = 90f;
                shake = 6f;
                rotateSpeed = 0.5f;
                shootCone = 3f;
                inaccuracy = 1.5f;

                consumePower(90f);
                consumeLiquid(Liquids.cryofluid, 3f);
                coolant = new ConsumeLiquid(Liquids.cryofluid, 2f);
                liquidCapacity = 360f;
                coolantMultiplier = 3f;

                heatColor = Color.valueOf("7fd6ff");
                shootSound = JBSounds.largeBeam;
                chargeSound = JBSounds.beam;
                loopSound = JBSounds.bioLoop;
                loopSoundVolume = 1.2f;

                shoot = new ShootPattern() {
                    {
                        firstShotDelay = 90f;
                    }
                };

                drawer = new DrawTurret() {
                    {
                        parts.add(new RegionPart("-glow") {
                            {
                                blending = Blending.additive;
                                color = heatColor;
                                progress = PartProgress.warmup;
                                outline = false;
                            }
                        });
                    }
                };

                shootEffect = new Effect(80f, e -> {
                    Draw.color(heatColor, Color.white, e.fin());
                    Lines.stroke(e.fout() * 6f);
                    Lines.circle(e.x, e.y, e.fin() * 180f);
                    Drawf.light(e.x, e.y, e.fin() * 220f, heatColor, 0.8f);
                });

                ammoUseEffect = new Effect(120f, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(Mathf.curve(e.fin(), 0, 1) * 4f);
                    Lines.circle(e.x, e.y, e.fout() * 140f);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fin() * 200f, heatColor, 0.8f);
                });

                shootType = JBBullets.chronosShell;
            }
        };

        antiMatterWarper = new AntiMatterWarper("anti-matter-warper") {
            {
                requirements(Category.units, ItemStack.with(
                        JBItems.singularium, 700,
                        JBItems.sergium, 1200,
                        JBItems.amalgam, 1000));
                size = 10;

                consumePower(200f);
                consumeItems(ItemStack.with(JBItems.singularium, 2));
                consumeLiquids(LiquidStack.with(JBLiquids.argon, 1f));
            }
        };

        apex = new PowerTurret("apex") {
            {
                requirements(Category.turret, with(
                        JBItems.singularium, 1400,
                        JBItems.sergium, 1200,
                        JBItems.pulsarite, 1100,
                        Items.surgeAlloy, 1200,
                        Items.phaseFabric, 800));

                size = 8;
                health = 9500;
                range = 1500f;
                reload = 420f;
                recoil = 14f;
                recoilTime = 140f;
                shake = 10f;
                rotateSpeed = 0.35f;
                shootCone = 2f;

                consumePower(120f);
                consumeLiquid(Liquids.cryofluid, 3f);
                coolant = new ConsumeLiquid(Liquids.cryofluid, 2.5f);
                liquidCapacity = 420f;
                coolantMultiplier = 3.2f;

                heatColor = Color.valueOf("ffb85c");
                shootSound = JBSounds.blastShockwave;
                chargeSound = JBSounds.largeBeam;

                shoot = new ShootPattern() {
                    {
                        firstShotDelay = 120f;
                    }
                };

                drawer = new DrawTurret() {
                    {
                        parts.add(new RegionPart("-glow") {
                            {
                                blending = Blending.additive;
                                color = heatColor;
                                progress = PartProgress.warmup;
                                outline = false;
                            }
                        });
                    }
                };

                shootEffect = new Effect(80f, e -> {
                    Draw.color(heatColor, Color.white, e.fin());
                    Lines.stroke(e.fout() * 8f);
                    Lines.circle(e.x, e.y, e.fin() * 220f);
                    Drawf.light(e.x, e.y, e.fin() * 260f, heatColor, 0.9f);
                });

                ammoUseEffect = new Effect(140f, e -> {
                    Draw.color(heatColor);
                    Lines.stroke(Mathf.curve(e.fin(), 0, 1) * 6f);
                    Lines.circle(e.x, e.y, e.fout() * 200f);
                    Draw.color(Color.white);
                    Drawf.light(e.x, e.y, e.fin() * 240f, heatColor, 0.9f);
                });

                shootType = JBBullets.apexShell;
            }
        };

        hastae = new ItemTurret("hastae") {
            {
                requirements(Category.turret, with(
                        Items.titanium, 250,
                        Items.thorium, 150,
                        Items.plastanium, 100,
                        JBItems.adamantium, 100,
                        JBItems.sergium, 500));

                size = 6;
                health = 2200;
                range = 2000f;
                reload = 1000f;

                recoil = 15f;
                recoilTime = 1000f;
                shake = 6f;
                rotateSpeed = 0.5f;

                heatColor = JBColor.yellow;

                consumePower(12f);
                shootSound = JBSounds.shootGauss3;

                shoot = new ShootPattern() {
                    {
                        firstShotDelay = 60f;
                    }
                };

                ammo(
                        Items.surgeAlloy, new BasicBulletType(75f, 11400) {
                            {
                                width = 15f;
                                height = 100f;
                                lifetime = 30f;

                                pierce = true;
                                pierceCap = -1;

                                frontColor = Color.white;
                                backColor = Color.orange;

                                trailColor = JBColor.yellow;
                                trailWidth = 7f;
                                trailLength = 60;

                                lightning = 5;
                                lightningDamage = 150;
                                lightningLength = 20;

                                hitEffect = Fx.instBomb;
                                hitSound = JBSounds.blast;
                                // shootEffect = Fx.shootBigType;
                                shootEffect = Fx.shootBigColor;
                                hitEffect = Fx.massiveExplosion;
                                despawnEffect = Fx.scatheExplosion;
                            }
                        });

                smokeEffect = Fx.coreExplosion;

            }

        };

        adamantiumSynthesizer = new EffectPowerGenerator("adamantium-synthesizer") {
            {
                requirements(Category.power, ItemStack.with(
                        JBItems.adamantium, 220,
                        JBItems.cryostal, 245,
                        JBItems.feronium, 340,
                        JBItems.thorium, 360));
                size = 3;
                itemCapacity = 30;
                scaledHealth = 15;
                powerProduction = 55f;
                updateEffect = JBFx.adamantiumSynthesizerWork;
                itemDuration = 120f;

                consumeItems(ItemStack.with(JBItems.adamantium, 2));
                consumeLiquids(LiquidStack.with(JBLiquids.cryofluid, 0.8f));

                drawer = new DrawMulti(
                        new DrawRegion(),
                        new DrawGlowRegion("-glow") {
                            {
                                color = Color.valueOf("#E02D2D");
                            }
                        }

                );
            }
        };
    }
}
