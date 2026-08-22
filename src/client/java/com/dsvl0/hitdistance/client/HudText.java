package com.dsvl0.hitdistance.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HudText {
    public String text;
    public int x, y;
    public int color;
    public boolean shadow;
    public float scale = 1f;

    private final Minecraft client;

    public HudText(String text, int x, int y, int color, boolean shadow, Minecraft mcClient) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
        this.client = mcClient;
    }

    public void render(GuiGraphics graphics) {
        var matrices = graphics.pose();
        matrices.pushMatrix();
        matrices.scale(scale, scale);

        graphics.drawString(
                client.font,
                text,
                (int)(x / scale),
                (int)(y / scale),
                color,
                shadow
        );

        matrices.popMatrix();
    }

    public int getCenterOptimalY() {
        return client.getWindow().getGuiScaledHeight() / 2;
    }

    public int getCenterOptimalX() {
        return client.getWindow().getGuiScaledWidth() / 2;
    }
}