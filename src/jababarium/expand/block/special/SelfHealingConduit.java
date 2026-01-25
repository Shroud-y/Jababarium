package jababarium.expand.block.special;

import arc.util.Time;
import mindustry.world.blocks.liquid.Conduit;

public class SelfHealingConduit extends Conduit {

    public float healPerSecond = 6f;

    public SelfHealingConduit(String name){
        super(name);

        health = 420;
        liquidCapacity = 155f;
        liquidPressure = 2.5f;

        leaks = false;
        rotate = true;
        solid = false;
    }

    public class SelfHealingConduitBuild extends ConduitBuild {

        @Override
        public void updateTile(){
            super.updateTile();

            if(damaged()){
                boolean hasLiquid =
                        liquids.current() != null &&
                                liquids.currentAmount() > 0.01f;

                if(hasLiquid){
                    float heal = healPerSecond * Time.delta;

                    if(liquids.current().temperature > 0.7f){
                        heal *= 0.6f;
                    }

                    heal(heal);
                }
            }
        }
    }
}
