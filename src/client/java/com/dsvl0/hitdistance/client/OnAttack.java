package com.dsvl0.hitdistance.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnAttack {
    private static boolean listenerRegistered = false;
    private float lastHealth = -1.0f;


    private static final List<DamageListener> listeners = new ArrayList<>();
    public interface DamageListener {
        void onDamage(MinecraftClient client, DamageSource source);
    }
    public static void addListener(DamageListener listener) {
        listeners.add(listener);
    }
    public static void event(MinecraftClient client, DamageSource source){
        for (DamageListener listener : listeners) {
            listener.onDamage(client, source);
        }

    }


    public void listen(){
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            listenerRegistered = false;
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (listenerRegistered) return;
            listenerRegistered = true;

            ClientTickEvents.END_CLIENT_TICK.register(mcclient -> {
                if (client.player == null || client.world == null) {
                    lastHealth = -1.0f;
                    return;
                }

                float currentHealth = client.player.getHealth();

                if (lastHealth < 0.0f) {
                    lastHealth = currentHealth;
                    return;
                }

                if (currentHealth < lastHealth) {
                    DamageSource source = client.player.getRecentDamageSource();
                    event(client, source);
                }

                lastHealth = currentHealth;
            });

        });
    }
}
