package com.orca.toolmemory.mixin;

import com.orca.toolmemory.ToolMemoryData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MiningSpeedMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMiningSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack heldItem = player.getMainHandStack();

        if (heldItem.getItem() instanceof MiningToolItem) {
            String blockType = block.getBlock().getTranslationKey();
            int xp = ToolMemoryData.getMiningXP(heldItem, blockType);
            float bonus = ToolMemoryData.getMiningSpeedBonus(xp);

            if (bonus > 0) {
                float currentSpeed = cir.getReturnValue();
                float newSpeed = currentSpeed * (1.0f + bonus);
                cir.setReturnValue(newSpeed);
            }
        }
    }
}
