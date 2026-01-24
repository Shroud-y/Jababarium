package jababarium.content;

import arc.math.Mathf;
import jababarium.expand.block.special.FluxReactor;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.gen.Bullet;
import jababarium.util.func.JBFunc;
import mindustry.entities.bullet.FlakBulletType;
import jababarium.expand.block.commandable.BombLauncher;
import jababarium.expand.bullets.LightningLinkerBulletType;
import jababarium.util.graphic.OptionalMultiEffect;
import mindustry.world.blocks.distribution.*;
import mindustry.world.draw.*;


public class JBBlocks {

    public static Block manualArtillery, cryostalConveyor, cryostalRouter, cryostalJunction, cryostalBridge, fluxReactor;

    public static void load() {

        manualArtillery = new BombLauncher("tochka-u") {
            {
                size = 3;
                storage = 4;

                requirements(
                        Category.defense,
                        ItemStack.with(
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

        cryostalBridge = new ItemBridge("cryostal-item-bridge"){{
            requirements(Category.distribution, ItemStack.with(
                    JBItems.cryostal, 3,
                    JBItems.adamantium, 3
            ));
            health = 15;
            range = 8;
            canOverdrive = true;
            itemCapacity = 14;
            transportTime = 5f;
        }};

        cryostalJunction = new Junction("cryostal-junction") {{
            requirements(Category.distribution, ItemStack.with(
                    JBItems.cryostal, 2,
                    JBItems.adamantium, 3
            ));
            health = 10;
            itemCapacity = 20;
        }};

        cryostalConveyor = new Conveyor("cryostal-conveyor") {{
            requirements(Category.distribution, ItemStack.with(
                    JBItems.cryostal, 1,
                    JBItems.adamantium, 1
            ));

            speed = 0.1f;
            displayedSpeed = 16;
            itemCapacity = 14;
            health = 10;
            canOverdrive = true;
            junctionReplacement = cryostalJunction;
            bridgeReplacement = cryostalBridge;
        }};

        cryostalRouter = new Router("cryostal-router") {{
            requirements(Category.distribution, ItemStack.with(
                    JBItems.cryostal, 2,
                    JBItems.adamantium, 2
            ));
            health = 15;
            canOverdrive = false;
            speed = 0f;
            itemCapacity = 20;
        }};

        fluxReactor = new FluxReactor("flux-reactor") {{
            requirements(Category.power, ItemStack.with(
                    JBItems.amalgam, 3000,
                    JBItems.sergium, 3500,
                    JBItems.pulsarite, 3000
            ));
            size = 9;
            coolant = JBLiquids.argon;
            ambientSound = JBSounds.fluxReactorWorking;

            consumeLiquid(JBLiquids.argon, 2f);
            consumeItems(ItemStack.with(JBItems.sergium, 2, JBItems.pulsarite, 3, JBItems.adamantium, 4));

        }};
    }
}