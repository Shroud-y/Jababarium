package jababarium.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
// import mindustry.gen.Sounds; // Цей імпорт виправляє помилку зі звуком!

public class JBBullets {

    public static BulletType burst;

    public static void load() {

        burst = new BasicBulletType(16f, 250) {
            { // Швидкість 16, Шкода 250
                // --- Основні параметри ---
                // Дальність турелі 1200 / Швидкість 16 = 75.
                // Ставимо 80, щоб точно долітав до краю радіусу.
                lifetime = 80f;

                width = 14f;
                height = 24f;
                shrinkY = 0.3f; // Робить снаряд гострим/витягнутим

                // --- Кольори та світло ---
                // Енергетичний оранжевий колір (можна змінити на свій)
                backColor = Color.valueOf("#5CE65C");
                frontColor = Color.white;

                // Снаряд світиться
                lightColor = backColor;
                lightOpacity = 0.7f;
                lightRadius = 40f;

                // --- Ефекти польоту (Трейл) ---
                trailWidth = 4.5f;
                trailLength = 25; // Довгий хвіст для відчуття швидкості
                trailColor = backColor;

                // --- Ефекти влучання ---
                hitSound = JBSounds.artilleryFire1; // Потужний звук
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootBigSmoke;
                hitEffect = Fx.massiveExplosion; // Великий вибух
                despawnEffect = Fx.scatheExplosion; // Красивий ефект при зникненні

                // --- Кластери (Осколки) ---
                fragBullets = 12; // Кількість осколків
                fragVelocityMin = 0.4f;
                fragVelocityMax = 4.5f;
                fragLifeMin = 20f;
                fragLifeMax = 40f;

                // Осколки теж завдають шкоди і мають ефект блискавок
                fragBullet = new BasicBulletType(5f, 40) {
                    {
                        width = 7f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 35f;

                        backColor = Color.valueOf("#5CE65C");
                        frontColor = Color.white;

                        trailWidth = 2f;
                        trailLength = 8;
                        trailColor = backColor;

                        // Додаємо блискавки для енергетичного ефекту
                        lightning = 2; // Кількість блискавок від кожного осколка
                        lightningLength = 6;
                        lightningColor = backColor;
                        lightningDamage = 20;

                        despawnEffect = Fx.none;
                        hitEffect = Fx.hitBulletColor;
                    }
                };
            }
        };
    }
}