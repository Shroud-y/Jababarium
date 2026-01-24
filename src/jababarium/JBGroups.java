package jababarium;

import arc.math.geom.Rect;

import arc.struct.Seq;

import jababarium.expand.block.commandable.CommandableBlock;

public class JBGroups {
    protected static final Rect tmpRect = new Rect();

    public static final Seq<CommandableBlock.CommandableBlockBuild> commandableBuilds = new Seq<>();

    public static void worldInit() {
    }

    public static void clear() {
        commandableBuilds.clear();
    }

    public static void worldReset() {
    }

    public static void update() {
    }

    public static void draw() {
    }

}