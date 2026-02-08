package jababarium.content;

import arc.audio.Sound;
import mindustry.Vars;

public class JBSounds {

    public static Sound artilleryFire1, artilleryFire2, artilleryFire3, artilleryOpen1, artilleryOpen2,
            fluxReactorExplosion,
            fluxReactorWorking, shootGauss1, shootGauss3, antimatter, missile, blast, blastShockwave, beam, bioLoop,
            largeBeam, hugeBlast;

    public static void load() {
        artilleryFire1 = loadSound("artilleryfire1");
        artilleryFire2 = loadSound("artilleryfire2");
        artilleryFire3 = loadSound("artilleryfire3");
        artilleryOpen1 = loadSound("artilleryopen1");
        artilleryOpen2 = loadSound("artilleryopen2");
        fluxReactorExplosion = loadSound("fluxreactorexplosion");
        fluxReactorWorking = loadSound("fluxreactorworking");
        shootGauss1 = loadSound("shootGauss1");
        shootGauss3 = loadSound("shootGauss3");
        antimatter = loadSound("antimatter");
        missile = loadSound("missile");
        blast = loadSound("blastSmoke");
        blastShockwave = loadSound("blastShockwave");
        beam = loadSound("beam");
        bioLoop = loadSound("bioLoop");
        largeBeam = loadSound("largeBeam");
        hugeBlast = loadSound("hugeBlast");
    }

    private static Sound loadSound(String name) {
        return new Sound(Vars.tree.get("sounds/" + name + ".ogg"));
    }
}
