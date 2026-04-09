package com.orca.toolmemory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolMemoryMod implements ModInitializer {
    public static final String MOD_ID = "tool-memory";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Tool Memory mod initialized! Your tools will now remember their experience.");

        // Register block break event for mining XP
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ItemStack heldItem = serverPlayer.getMainHandStack();
                if (heldItem.getItem() instanceof MiningToolItem) {
                    String blockType = state.getBlock().getTranslationKey();
                    ToolMemoryData.addMiningXP(heldItem, blockType, 1);
                }
            }
        });

        // Register entity death event for combat XP
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (damageSource.getAttacker() instanceof ServerPlayerEntity player) {
                ItemStack heldItem = player.getMainHandStack();
                if (heldItem.getItem() instanceof SwordItem) {
                    String mobType = entity.getType().getTranslationKey();
                    ToolMemoryData.addCombatXP(heldItem, mobType, 1);
                }
            }
        });
    }
}
