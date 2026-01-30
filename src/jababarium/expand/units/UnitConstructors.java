package jababarium.expand.units;

import arc.struct.Seq;
import jababarium.content.JBItems;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;

import static jababarium.content.JBUnits.*;

public class UnitConstructors {

    public static Block groundForge, TideForge, SkyForge;

    public static void load(){

        SkyForge = new UnitFactory("sky-forge"){{
            requirements(Category.units, ItemStack.with(
                    JBItems.feronium, 300,
                    JBItems.plastanium, 240,
                    JBItems.silicon, 330,
                    JBItems.titanium, 400
            ));
            size = 3;
            health = 300;
            consumePower(8f);

            plans = Seq.with(
                    new UnitPlan(
                            scout,
                            60f * 13,
                            ItemStack.with(
                                    JBItems.silicon, 25,
                                    JBItems.lead, 20
                            )
                    ),
                    new UnitPlan(
                            zanuka,
                            60f * 15,
                            ItemStack.with(
                                    JBItems.graphite, 25,
                                    JBItems.metaglass, 20
                            )));

        }};
    }
}
