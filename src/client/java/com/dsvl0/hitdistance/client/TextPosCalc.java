package com.dsvl0.hitdistance.client;

import com.dsvl0.hitdistance.client.modmenu.ModMenuConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font; // было: net.minecraft.client.font.TextRenderer

public class TextPosCalc {
    private static Minecraft client;
    private static Font font; // было: TextRenderer textRenderer

    public static void setClient(Minecraft globalClient) {
        client = globalClient;
        if (client == null) client = Minecraft.getInstance();
        font = client.font; // было: client.textRenderer
    }

    private static Font getFont() { // было: getTextRenderer()
        if (client == null) client = Minecraft.getInstance();
        return client.font; // было: client.textRenderer
    }

    public static int getX(String text, HudText textView, ModMenuConfigScreen.Pos pos) {
        if (font == null) setClient(client);

        return switch (pos) {
            case CROSS_LEFT   -> textView.getCenterOptimalX() - getFont().width(text) - 6;
            case CROSS_RIGHT  -> textView.getCenterOptimalX() + 6;
            case CROSS_BOTTOM,
                 CROSS_TOP,
                 BAR_TOP      -> textView.getCenterOptimalX() - (getFont().width(text) / 2);
            case BAR_END      -> HotbarBounds.getRight() + 4;
            default           -> textView.getCenterOptimalX() - (getFont().width(text) / 2);
        };
    }

    public static int getY(String text, HudText textView, ModMenuConfigScreen.Pos pos) {
        if (font == null) setClient(client);

        return switch (pos) {
            case CROSS_LEFT,
                 CROSS_RIGHT  -> textView.getCenterOptimalY() - (getFont().lineHeight / 2) + 2;
            case CROSS_BOTTOM -> textView.getCenterOptimalY() + 8;
            case CROSS_TOP    -> textView.getCenterOptimalY() - 12;
            case BAR_TOP      -> HotbarBounds.getTop() - getFont().lineHeight - 16;
            case BAR_END      -> HotbarBounds.getBottom() - getFont().lineHeight - 3;
            default           -> textView.getCenterOptimalX() - (getFont().width(text) / 2);
        };
    }
}