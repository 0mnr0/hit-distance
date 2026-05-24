package com.dsvl0.hitdistance.client;

import com.dsvl0.hitdistance.client.modmenu.ModMenuConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

public class TextPosCalc {
    private static MinecraftClient client;
    private static TextRenderer textRenderer;
    public static void setClient(MinecraftClient globalClient) {
        client = globalClient;
        if (client == null) client = MinecraftClient.getInstance();
        textRenderer = client.textRenderer;
    }

    private static TextRenderer getTextRenderer() {
        if (client == null) client = MinecraftClient.getInstance();
        return client.textRenderer;
    }




    public static int getX(String text, HudText textView, ModMenuConfigScreen.Pos Pos) {
        // warnText.x = warnText.getCenterOptimalX() + 6;
        if (textRenderer == null) {setClient(client);}

        int position;
        switch (Pos) {
            case CROSS_LEFT -> {
                position = textView.getCenterOptimalX() - getTextRenderer().getWidth(text) - 6;
            }
            case CROSS_RIGHT -> {
                position = textView.getCenterOptimalX() + 6;
            }
            case CROSS_BOTTOM, CROSS_TOP, BAR_TOP -> {
                position = textView.getCenterOptimalX() - (getTextRenderer().getWidth(text) / 2);
            }
//          case BAR_START -> {
//              position = HotbarBounds.getLeft() - getTextRenderer().getWidth(text) - 10;
//          }
            case BAR_END -> {
                position = HotbarBounds.getRight() + 4;
            }

            default -> position = textView.getCenterOptimalX() - (getTextRenderer().getWidth(text) / 2);
        }


        return position;
    }



    public static int getY(String text, HudText textView, ModMenuConfigScreen.Pos Pos) {
        if (textRenderer == null) {setClient(client);}

        int position;
        switch (Pos) {
            case CROSS_LEFT, CROSS_RIGHT -> {
                position = textView.getCenterOptimalY() - (getTextRenderer().fontHeight / 2) + 2;
            }
            case CROSS_BOTTOM -> {
                position = textView.getCenterOptimalY() + 8;
            }
            case CROSS_TOP -> {
                position = textView.getCenterOptimalY() - 12;
            }
            case BAR_TOP -> {
                position = HotbarBounds.getTop() - getTextRenderer().fontHeight - 16;
            }
            case BAR_END -> { //BAR_START
                position = HotbarBounds.getBottom() - getTextRenderer().fontHeight - 3;
            }
            default -> position = textView.getCenterOptimalX() - (getTextRenderer().getWidth(text) / 2);
        }


        return position;
    }
}
