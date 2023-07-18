package com.trbz_.simplysteel.inventory;

import com.trbz_.simplysteel.blocks.SteelAnvilBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class SteelAnvilMenu extends AnvilMenu {

    private float breakChanceMultiplier = 1.0f;

    public SteelAnvilMenu(int p_39005_, Inventory p_39006_) {
        this(p_39005_, p_39006_, ContainerLevelAccess.NULL, 1.0f);
    }

    public SteelAnvilMenu(int p_39008_, Inventory p_39009_, ContainerLevelAccess p_39010_, float bcm) {
        super(p_39008_, p_39009_, p_39010_);
        this.breakChanceMultiplier = bcm;
    }

    protected void onTake(Player p_150474_, ItemStack p_150475_) {
        if (!p_150474_.getAbilities().instabuild) {
            p_150474_.giveExperienceLevels(-this.cost.get());
        }

        float breakChance = breakChanceMultiplier * net.minecraftforge.common.ForgeHooks.onAnvilRepair(p_150474_, p_150475_, SteelAnvilMenu.this.inputSlots.getItem(0), SteelAnvilMenu.this.inputSlots.getItem(1));

        this.inputSlots.setItem(0, ItemStack.EMPTY);
        if (this.repairItemCountCost > 0) {
            ItemStack itemstack = this.inputSlots.getItem(1);
            if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                itemstack.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, itemstack);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        this.cost.set(0);
        this.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if (!p_150474_.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && p_150474_.getRandom().nextFloat() < breakChance) {
                BlockState blockstate1 = SteelAnvilBlock.damage(blockstate);
                if (blockstate1 == null) {
                    p_150479_.removeBlock(p_150480_, false);
                    p_150479_.levelEvent(1029, p_150480_, 0);
                } else {
                    p_150479_.setBlock(p_150480_, blockstate1, 2);
                    p_150479_.levelEvent(1030, p_150480_, 0);
                }
            } else {
                p_150479_.levelEvent(1030, p_150480_, 0);
            }

        });
    }

}
