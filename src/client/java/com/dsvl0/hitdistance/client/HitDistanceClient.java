package com.dsvl0.hitdistance.client;

import com.dsvl0.hitdistance.client.modmenu.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class HitDistanceClient implements ClientModInitializer {
	MinecraftClient client;
	private HudText warnText;
	private int timer = 0;
	private boolean halfAlphaAnim = false;
	private String text = "";
	private static final float MAX_SURVIVAL_DISTANCE_HIT = 3.0f;


	public int getColor(int rgb, int percent) {
		int alpha = (int) (percent / 100.0f * 255);
		return (alpha << 24) | (rgb & 0x00FFFFFF);
	}

	@Override
	public void onInitializeClient() {
		ModConfig.load();
		client =  MinecraftClient.getInstance();
		TextPosCalc.setClient(client);
		warnText = new HudText(text, 20, 20, 0xFFFFFFFF, false, client);

		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			if (warnText.text != null && !warnText.text.isEmpty()) {
				warnText.render(drawContext, client.textRenderer);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (ModConfig.INSTANCE.isModEnabled) {
				if (timer <= 0) {
					text = "";
				} else if (ModConfig.INSTANCE.hideAfterTime) {
					timer--;
					if (timer <= 20 && !halfAlphaAnim) {
						warnText.color = getColor(warnText.color, (timer * 5));
					}
					if (timer <= 10 && halfAlphaAnim) {
						warnText.color = getColor(warnText.color, (timer * 5));
					}
				}
				warnText.text = text;
			} else {
				text = "";
			}
		});

		new OnAttack().listen();
		OnAttack.addListener(this::newAttack);

	}


	public void setWarn(double distance) {

		distance = Math.round(distance * 10.0) / 10.0;
		text = String.valueOf(distance);
		warnText.color = getColor(warnText.color, 100);
		warnText.x = TextPosCalc.getX(text, warnText, ModConfig.INSTANCE.position);
		warnText.y = TextPosCalc.getY(text, warnText, ModConfig.INSTANCE.position);
		timer = 55;
		halfAlphaAnim = false;
	}


	public void newAttack(MinecraftClient client, DamageSource source){
		if (
				(source != null ? source.getName() : null) != null &&  // Check that getName will return String
						Objects.equals(source.getName(), "player") &&
						client.player != null

		) {

			Entity attacker = source.getSource();
			if (attacker != null && attacker.isPlayer()) {
				double distance = distanceBetweenHitboxes(
						client.player,
						attacker
				);
				if (distance < 3.05 && distance >= 3) {distance = 3;}


				boolean isInCreative = false;
				LivingEntity attackerEntity = attacker.getEntity();
				if (attackerEntity instanceof PlayerEntity player) {
					isInCreative = player.isCreative();
				}
				boolean showIfNormal = ModConfig.INSTANCE.showOnNormalHits;


				if ((isInCreative && !ModConfig.INSTANCE.ignoreCreativeMode) || !isInCreative) {
					if (distance > MAX_SURVIVAL_DISTANCE_HIT) {
						setWarn(distance);
						warnText.color = 0xFFFF8F8F;
					} else if (showIfNormal) {
						setWarn(distance);
						halfAlphaAnim = true;
						timer = 40;
						warnText.color = 0x80FFFFFF;
					}
				}

			}
		}
	}


	public static double distanceBetweenHitboxes(PlayerEntity a, Entity b) {
		Box boxA = a.getBoundingBox();
		Box boxB = b.getBoundingBox();

		double dx = Math.max(0.0, Math.max(boxA.minX - boxB.maxX, boxB.minX - boxA.maxX));
		double dy = Math.max(0.0, Math.max(boxA.minY - boxB.maxY, boxB.minY - boxA.maxY));
		double dz = Math.max(0.0, Math.max(boxA.minZ - boxB.maxZ, boxB.minZ - boxA.maxZ));

		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
}