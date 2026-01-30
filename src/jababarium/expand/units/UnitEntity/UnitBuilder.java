package jababarium.expand.units.UnitEntity;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.entities.abilities.Ability;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class UnitBuilder {

    private final UnitType unit;

    private UnitBuilder(String name){
        unit = new UnitType(name);
        unit.engines = new Seq<>();
    }

    public static UnitBuilder create(String name){
        return new UnitBuilder(name);
    }

    public UnitBuilder flying(){
        unit.flying = true;
        unit.lowAltitude = true;
        return this;
    }

    public UnitBuilder health(float hp){
        unit.health = hp;
        return this;
    }

    public UnitBuilder speed(float speed){
        unit.speed = speed;
        return this;
    }

    public UnitBuilder armor(float armor){
        unit.armor = armor;
        return this;
    }

    public UnitBuilder outlineRadius(int radius){
        unit.outlineRadius = radius;
        return this;
    }

    public UnitBuilder engine(int count, float length, Color color){
        unit.engineSize = length;
        unit.engineColor = color;

        unit.engines.clear();

        for(int i = 0; i < count; i++){
            float offset = (i - (count - 1) / 2f) * 4f;

            unit.engines.add(new UnitType.UnitEngine(
                    offset,
                    -unit.hitSize / 2f,
                    length / 2f,
                    270f
            ));
        }

        return this;
    }

    public UnitBuilder weapon(Weapon weapon){
        unit.weapons.add(weapon);
        return this;
    }

    public UnitBuilder ability(Ability ability){
        unit.abilities.add(ability);
        return this;
    }

    public UnitType build(){
        return unit;
    }

    public UnitBuilder hitSize(float size){
        unit.hitSize = size;
        return this;
    }
}