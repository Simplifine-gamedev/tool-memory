package com.orca.toolmemory;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ToolMemoryData {
    private static final String TOOL_MEMORY_KEY = "ToolMemory";
    private static final String MINING_XP_KEY = "MiningXP";
    private static final String COMBAT_XP_KEY = "CombatXP";

    public static final int MAX_LEVEL = 3;

    // Mining thresholds and bonuses
    public static final int[] MINING_THRESHOLDS = {100, 500, 1000};
    public static final float[] MINING_SPEED_BONUSES = {0.2f, 0.4f, 0.6f};

    // Combat thresholds and bonuses
    public static final int[] COMBAT_THRESHOLDS = {50, 150, 300};
    public static final float[] COMBAT_DAMAGE_BONUSES = {1.0f, 2.0f, 3.0f};

    public static NbtCompound getToolMemoryNbt(ItemStack stack) {
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component != null) {
            NbtCompound nbt = component.copyNbt();
            if (nbt.contains(TOOL_MEMORY_KEY)) {
                return nbt.getCompound(TOOL_MEMORY_KEY);
            }
        }
        return new NbtCompound();
    }

    public static void setToolMemoryNbt(ItemStack stack, NbtCompound toolMemory) {
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        NbtCompound nbt;
        if (component != null) {
            nbt = component.copyNbt();
        } else {
            nbt = new NbtCompound();
        }
        nbt.put(TOOL_MEMORY_KEY, toolMemory);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    public static int getMiningXP(ItemStack stack, String blockType) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        if (toolMemory.contains(MINING_XP_KEY)) {
            NbtCompound miningXp = toolMemory.getCompound(MINING_XP_KEY);
            return miningXp.getInt(blockType);
        }
        return 0;
    }

    public static void addMiningXP(ItemStack stack, String blockType, int amount) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        NbtCompound miningXp;
        if (toolMemory.contains(MINING_XP_KEY)) {
            miningXp = toolMemory.getCompound(MINING_XP_KEY);
        } else {
            miningXp = new NbtCompound();
        }

        int current = miningXp.getInt(blockType);
        int newXp = current + amount;
        miningXp.putInt(blockType, newXp);
        toolMemory.put(MINING_XP_KEY, miningXp);
        setToolMemoryNbt(stack, toolMemory);
    }

    public static int getCombatXP(ItemStack stack, String mobType) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        if (toolMemory.contains(COMBAT_XP_KEY)) {
            NbtCompound combatXp = toolMemory.getCompound(COMBAT_XP_KEY);
            return combatXp.getInt(mobType);
        }
        return 0;
    }

    public static void addCombatXP(ItemStack stack, String mobType, int amount) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        NbtCompound combatXp;
        if (toolMemory.contains(COMBAT_XP_KEY)) {
            combatXp = toolMemory.getCompound(COMBAT_XP_KEY);
        } else {
            combatXp = new NbtCompound();
        }

        int current = combatXp.getInt(mobType);
        int newXp = current + amount;
        combatXp.putInt(mobType, newXp);
        toolMemory.put(COMBAT_XP_KEY, combatXp);
        setToolMemoryNbt(stack, toolMemory);
    }

    public static int getMiningLevel(int xp) {
        for (int i = MINING_THRESHOLDS.length - 1; i >= 0; i--) {
            if (xp >= MINING_THRESHOLDS[i]) {
                return Math.min(i + 1, MAX_LEVEL);
            }
        }
        return 0;
    }

    public static int getCombatLevel(int xp) {
        for (int i = COMBAT_THRESHOLDS.length - 1; i >= 0; i--) {
            if (xp >= COMBAT_THRESHOLDS[i]) {
                return Math.min(i + 1, MAX_LEVEL);
            }
        }
        return 0;
    }

    public static float getMiningSpeedBonus(int xp) {
        int level = getMiningLevel(xp);
        if (level > 0 && level <= MINING_SPEED_BONUSES.length) {
            return MINING_SPEED_BONUSES[level - 1];
        }
        return 0f;
    }

    public static float getCombatDamageBonus(int xp) {
        int level = getCombatLevel(xp);
        if (level > 0 && level <= COMBAT_DAMAGE_BONUSES.length) {
            return COMBAT_DAMAGE_BONUSES[level - 1];
        }
        return 0f;
    }

    public static NbtCompound getAllMiningXP(ItemStack stack) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        if (toolMemory.contains(MINING_XP_KEY)) {
            return toolMemory.getCompound(MINING_XP_KEY);
        }
        return new NbtCompound();
    }

    public static NbtCompound getAllCombatXP(ItemStack stack) {
        NbtCompound toolMemory = getToolMemoryNbt(stack);
        if (toolMemory.contains(COMBAT_XP_KEY)) {
            return toolMemory.getCompound(COMBAT_XP_KEY);
        }
        return new NbtCompound();
    }
}
