package com.trbz_.simplysteel.setup;

import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class DispenserRegister {
    public static class FirestarterDispenseItemBehavior extends OptionalDispenseItemBehavior{
        protected ItemStack execute(BlockSource p_123412_, ItemStack p_123413_) {
            Level level = p_123412_.getLevel();
            this.setSuccess(true);
            Direction direction = p_123412_.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos blockpos = p_123412_.getPos().relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            if (BaseFireBlock.canBePlacedAt(level, blockpos, direction)) {
                level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(level, blockpos));
                level.gameEvent((Entity)null, GameEvent.BLOCK_PLACE, blockpos);
            } else if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
                if (blockstate.isFlammable(level, blockpos, p_123412_.getBlockState().getValue(DispenserBlock.FACING).getOpposite())) {
                    blockstate.onCaughtFire(level, blockpos, p_123412_.getBlockState().getValue(DispenserBlock.FACING).getOpposite(), null);
                    if (blockstate.getBlock() instanceof TntBlock)
                        level.removeBlock(blockpos, false);
                } else {
                    this.setSuccess(false);
                }
            } else {
                level.setBlockAndUpdate(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)));
                level.gameEvent((Entity)null, GameEvent.BLOCK_CHANGE, blockpos);
            }

            if (this.isSuccess() && p_123413_.hurt(1, level.random, (ServerPlayer)null)) {
                p_123413_.setCount(0);
            }

            return p_123413_;
        }
    };
    public static void init(){
        DispenserBlock.registerBehavior((RegistryHandler.STEEL_SHEARS.get()), new ShearsDispenseItemBehavior());
        DispenserBlock.registerBehavior((RegistryHandler.FLINT_AND_IRON.get()), new FirestarterDispenseItemBehavior());
        DispenserBlock.registerBehavior((RegistryHandler.QUARTZ_AND_IRON.get()), new FirestarterDispenseItemBehavior());
        DispenserBlock.registerBehavior((RegistryHandler.QUARTZ_AND_STEEL.get()), new FirestarterDispenseItemBehavior());
    }
}
