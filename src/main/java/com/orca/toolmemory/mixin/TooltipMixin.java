package com.orca.toolmemory.mixin;

import com.orca.toolmemory.ToolMemoryData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class TooltipMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void addToolMemoryTooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        List<Text> tooltip = cir.getReturnValue();

        if (stack.getItem() instanceof MiningToolItem) {
            NbtCompound miningXp = ToolMemoryData.getAllMiningXP(stack);
            if (!miningXp.isEmpty()) {
                tooltip.add(Text.literal("").append(Text.literal("Tool Memory:").formatted(Formatting.GOLD)));
                for (String key : miningXp.getKeys()) {
                    int xp = miningXp.getInt(key);
                    int level = ToolMemoryData.getMiningLevel(xp);
                    float bonus = ToolMemoryData.getMiningSpeedBonus(xp);
                    String blockName = formatBlockName(key);
                    String bonusText = bonus > 0 ? String.format(" (+%.0f%% speed)", bonus * 100) : "";
                    tooltip.add(Text.literal("  " + blockName + ": " + xp + " XP (Lv." + level + ")" + bonusText)
                            .formatted(level > 0 ? Formatting.GREEN : Formatting.GRAY));
                }
            }
        }

        if (stack.getItem() instanceof SwordItem) {
            NbtCompound combatXp = ToolMemoryData.getAllCombatXP(stack);
            if (!combatXp.isEmpty()) {
                tooltip.add(Text.literal("").append(Text.literal("Combat Memory:").formatted(Formatting.RED)));
                for (String key : combatXp.getKeys()) {
                    int xp = combatXp.getInt(key);
                    int level = ToolMemoryData.getCombatLevel(xp);
                    float bonus = ToolMemoryData.getCombatDamageBonus(xp);
                    String mobName = formatMobName(key);
                    String bonusText = bonus > 0 ? String.format(" (+%.0f dmg)", bonus) : "";
                    tooltip.add(Text.literal("  " + mobName + ": " + xp + " kills (Lv." + level + ")" + bonusText)
                            .formatted(level > 0 ? Formatting.GREEN : Formatting.GRAY));
                }
            }
        }
    }

    private String formatBlockName(String translationKey) {
        String name = translationKey.replace("block.minecraft.", "").replace("_", " ");
        if (name.length() > 0) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        return name;
    }

    private String formatMobName(String translationKey) {
        String name = translationKey.replace("entity.minecraft.", "").replace("_", " ");
        if (name.length() > 0) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        return name;
    }
}
