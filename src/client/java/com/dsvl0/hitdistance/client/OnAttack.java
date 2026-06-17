package com.dsvl0.hitdistance.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;
import java.util.List;

public class OnAttack {
    private static boolean listenerRegistered = false;
    private float lastHealth = -1.0f;


    private static final List<DamageListener> listeners = new ArrayList<>();
    public interface DamageListener {
        void onDamage(Minecraft client, DamageSource source);
    }
    public static void addListener(DamageListener listener) {
        listeners.add(listener);
    }
    public static void event(Minecraft client, DamageSource source){
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
                if (client.player == null || mcclient.level == null) {
                    lastHealth = -1.0f;
                    return;
                }

                float currentHealth = client.player.getHealth();

                if (lastHealth < 0.0f) {
                    lastHealth = currentHealth;
                    return;
                }

                if (currentHealth < lastHealth) {
                    DamageSource source = client.player.getLastDamageSource();
                    event(client, source);
                }

                lastHealth = currentHealth;
            });

        });
    }
}
