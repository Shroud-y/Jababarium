package jababarium.content;

import arc.audio.Sound;
import mindustry.Vars;

public class JBSounds {

    public static Sound artilleryFire1,
            artilleryFire2,
            artilleryFire3,
            artilleryOpen1,
            artilleryOpen2;

    public static void load() {
        artilleryFire1 = loadSound("artilleryfire1");
        artilleryFire2 = loadSound("artilleryfire2");
        artilleryFire3 = loadSound("artilleryfire3");
        artilleryOpen1 = loadSound("artilleryopen1");
        artilleryOpen2 = loadSound("artilleryopen2");
    }

    private static Sound loadSound(String name) {
        return new Sound(Vars.tree.get("sounds/" + name + ".ogg"));
    }
}
