package com.trbz_.simplysteel.blocks;

import com.trbz_.simplysteel.entities.FallingSteelAnvil;
import com.trbz_.simplysteel.inventory.SteelAnvilMenu;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SteelAnvilBlock extends AnvilBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.repair");

    public SteelAnvilBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    @Override
    public void tick(BlockState p_221124_, ServerLevel p_221125_, BlockPos p_221126_, RandomSource p_221127_) {
        if (isFree(p_221125_.getBlockState(p_221126_.below())) && p_221126_.getY() >= p_221125_.getMinBuildHeight()) {
            FallingSteelAnvil fallingsteelanvil = FallingSteelAnvil.fall(p_221125_, p_221126_, p_221124_);
            this.falling(fallingsteelanvil);
        }
    }
}
