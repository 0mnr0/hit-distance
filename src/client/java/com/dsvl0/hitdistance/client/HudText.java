package com.dsvl0.hitdistance.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class HudText {
    public String text;
    public int x, y;
    public int color;
    public boolean shadow;
    TextRenderer Renderer;
    MinecraftClient client;
    public float scale = 1f;

    public HudText(String text, int x, int y, int color, boolean shadow, MinecraftClient MCClient) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
        client = MCClient;
    }

    public void render(DrawContext ctx, TextRenderer renderer) {
        Renderer = renderer;

        ctx.getMatrices().pushMatrix();

        ctx.getMatrices().scale(scale, scale);

        ctx.drawText(
                renderer,
                text,
                (int)(x / scale),
                (int)(y / scale),
                color,
                shadow
        );

        ctx.getMatrices().popMatrix();
    }


    public int getCenterOptimalY (){
        int screenHeight = client.getWindow().getScaledHeight();
        return screenHeight / 2;
    }

    public int getCenterOptimalX (){
        int screenWidth = client.getWindow().getScaledWidth();
        return screenWidth / 2;
    }
}