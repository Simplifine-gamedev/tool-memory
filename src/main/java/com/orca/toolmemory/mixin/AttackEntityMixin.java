package com.orca.toolmemory.mixin;

import com.orca.toolmemory.ToolMemoryData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class AttackEntityMixin {

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Entity attacker = source.getAttacker();

        if (attacker instanceof PlayerEntity player) {
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof SwordItem) {
                String mobType = entity.getType().getTranslationKey();
                int xp = ToolMemoryData.getCombatXP(heldItem, mobType);
                float bonus = ToolMemoryData.getCombatDamageBonus(xp);

                if (bonus > 0) {
                    return amount + bonus;
                }
            }
        }
        return amount;
    }
}
