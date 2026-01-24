package jababarium;

import arc.*;
import arc.util.*;
import jababarium.content.*;
import jababarium.expand.block.CraftingBlock;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class Jababarium extends Mod {
    public static final String MOD_NAME = "jababarium";
    public static Mods.LoadedMod MOD;

    public Jababarium() {

        Log.info("Loaded ExampleJavaMod constructor.");

        // listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            // show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                // mod sprites are prefixed with the mod name (this mod is called
                // 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("jababarium-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent() {
        JBItems.load();
        JBLiquids.load();
        JBSounds.load();
        JBBullets.load();
        JBBlocks.load();
        JBContent.loadPriority();
        CraftingBlock.load();
        JBOres.load();
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }

}
