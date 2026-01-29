package jababarium.expand.units.UnitEntity;

import arc.audio.Sound;
import mindustry.entities.bullet.*;
import mindustry.type.Weapon;

import static mindustry.gen.Groups.bullet;

public class WeaponBuilder {
    Weapon w;

    public static WeaponBuilder create(String name){
        WeaponBuilder b = new WeaponBuilder();
        b.w = new Weapon(name);
        return b;
    }

    public WeaponBuilder reload(float r){
        w.reload = r;
        return this;
    }

    public WeaponBuilder range(float range){
        BulletType b = w.bullet;

        if(b instanceof BasicBulletType bb){
            bb.lifetime = range / bb.speed;

        }else if(b instanceof ArtilleryBulletType ab){
            ab.lifetime = range / ab.speed;

        }else if(b instanceof MissileBulletType mb){
            mb.lifetime = range / mb.speed;

        }else if(b instanceof LaserBulletType lb){
            lb.length = range;

        }else{
            throw new IllegalStateException(
                    "Range is not supported for bullet type: " + b.getClass().getSimpleName()
            );
        }

        return this;
    }

    public WeaponBuilder bullet(BulletType b){
        w.bullet = b;
        return this;
    }

    public WeaponBuilder shootSound(Sound s){
        w.shootSound = s;
        return this;
    }

    public Weapon build(){
        return w;
    }
}
