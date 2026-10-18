package com.dsvl0.hitdistance.client;

import net.minecraft.client.Minecraft;

public class HotbarBounds {
    private static final int HOTBAR_WIDTH  = 182;
    private static final int HOTBAR_HEIGHT = 22;

    public static int getLeft() {
        return getScaledWidth() / 2 - HOTBAR_WIDTH / 2;
    }

    public static int getRight() {
        return getScaledWidth() / 2 + HOTBAR_WIDTH / 2;
    }

    public static int getTop() {
        return getScaledHeight() - HOTBAR_HEIGHT;
    }

    public static int getBottom() {
        return getScaledHeight();
    }

    private static int getScaledWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    private static int getScaledHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }
}
