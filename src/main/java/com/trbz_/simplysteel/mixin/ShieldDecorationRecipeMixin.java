package com.trbz_.simplysteel.mixin;

import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShieldDecorationRecipe.class)
public class ShieldDecorationRecipeMixin {
    @Redirect(
            method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean injected1(ItemStack itemstack2, Item item) {
        return itemstack2.getItem() == Items.SHIELD || itemstack2.getItem() == RegistryHandler.STEEL_SHIELD.get();
    }

    @Redirect(
            method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean injected2(ItemStack itemstack2, Item item) {
        return itemstack2.getItem() == Items.SHIELD || itemstack2.getItem() == RegistryHandler.STEEL_SHIELD.get();
    }

}
