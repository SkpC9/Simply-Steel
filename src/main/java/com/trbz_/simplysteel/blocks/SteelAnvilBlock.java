package com.trbz_.simplysteel.blocks;

import com.trbz_.simplysteel.entities.FallingSteelAnvil;
import com.trbz_.simplysteel.inventory.SteelAnvilMenu;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Random;

public class SteelAnvilBlock extends AnvilBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.repair");

    public SteelAnvilBlock(BlockBehaviour.Properties props){
        super(props);
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState p_48821_, Level p_48822_, BlockPos p_48823_) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> new SteelAnvilMenu(p_48785_, p_48786_, ContainerLevelAccess.create(p_48822_, p_48823_), 0.5f), CONTAINER_TITLE);
    }

    @Nullable
    public static BlockState damage(BlockState blockState) {
        if (blockState.is(RegistryHandler.STEEL_ANVIL.get())) {
            return RegistryHandler.CHIPPED_STEEL_ANVIL.get().defaultBlockState().setValue(FACING, blockState.getValue(FACING));
        } else {
            return blockState.is(RegistryHandler.CHIPPED_STEEL_ANVIL.get()) ? RegistryHandler.DAMAGED_STEEL_ANVIL.get().defaultBlockState().setValue(FACING, blockState.getValue(FACING)) : null;
        }
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        if (isFree(serverLevel.getBlockState(blockPos.below())) && blockPos.getY() >= 0) {
            FallingSteelAnvil fallingsteelanvil = new FallingSteelAnvil(serverLevel, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, serverLevel.getBlockState(blockPos), 0.5f);
            this.falling(fallingsteelanvil);
            serverLevel.addFreshEntity(fallingsteelanvil);
        }
    }
}
