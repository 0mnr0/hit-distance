package com.dsvl0.hitdistance.client.modmenu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    public static ModConfig INSTANCE = new ModConfig();

    public boolean isModEnabled = true;
    public boolean ignoreCreativeMode = true;
    public boolean showOnNormalHits = true;
    public boolean hideAfterTime = true;
    public ModMenuConfigScreen.Pos position = ModMenuConfigScreen.Pos.CROSS_RIGHT;

    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("hitdist.json");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }
        try {
            String json = Files.readString(CONFIG_PATH);
            INSTANCE = GSON.fromJson(json, ModConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}