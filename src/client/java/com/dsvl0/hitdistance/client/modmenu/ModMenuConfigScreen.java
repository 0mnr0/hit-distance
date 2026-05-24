package com.dsvl0.hitdistance.client.modmenu;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.DescriptionWithName;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuConfigScreen {

    public static Screen create(Screen parent) {
        ModConfig cfg = ModConfig.INSTANCE;

        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("HitDistance Settings"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("General"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Enable mod"))
                                .binding(true,
                                        () -> cfg.isModEnabled,
                                        v  -> cfg.isModEnabled = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Ignore players in creative gamemode"))
                                .binding(true,
                                        () -> cfg.ignoreCreativeMode,
                                        v  -> cfg.ignoreCreativeMode = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Hide after some time"))
                                .binding(true,
                                        () -> cfg.hideAfterTime,
                                        v  -> cfg.hideAfterTime = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show text on normal hits"))
                                .binding(true,
                                        () -> cfg.showOnNormalHits,
                                        v  -> cfg.showOnNormalHits = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Pos>createBuilder()
                                .name(Text.literal("Position"))
                                .binding(Pos.CROSS_RIGHT,
                                        () -> cfg.position,
                                        v  -> cfg.position = v)
                                .controller(opt -> EnumControllerBuilder.create(opt)
                                        .enumClass(Pos.class)
                                        .formatValue(v -> Text.literal(v.getDisplayName())))
                                .build())

                        .build())
                .save(ModConfig::save)
                .build()
                .generateScreen(parent);
    }

    public enum Pos {
        CROSS_TOP, CROSS_BOTTOM, CROSS_LEFT, CROSS_RIGHT, BAR_TOP, BAR_END; // , BAR_START

        public String getDisplayName() {
            return switch (this) {
                case CROSS_TOP    -> "Over the crosshair";
                case CROSS_BOTTOM -> "Under the crosshair";
                case CROSS_LEFT   -> "To the left of the crosshair";
                case CROSS_RIGHT  -> "To the right of the crosshair";
                case BAR_TOP      -> "Above the hotbar";
                //case BAR_START    -> "To the left of the hotbar";
                case BAR_END      -> "To the right of the hotbar";
            };
        }
    }
}