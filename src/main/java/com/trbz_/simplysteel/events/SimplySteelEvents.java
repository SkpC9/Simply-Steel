package com.trbz_.simplysteel.events;

import com.trbz_.simplysteel.entities.SteelGolem;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SimplySteelEvents {

    private BlockPattern createSteelGolemFull() {
        return BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(CarvedPumpkinBlock.PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(RegistryHandler.STEEL_BLOCK.get()))).where('~', (p_284868_) -> p_284868_.getState().isAir()).build();
    }

    @SubscribeEvent
    public void spawnSteelGolemEvent(BlockEvent.EntityPlaceEvent event) {
        Block block = event.getPlacedBlock().getBlock();
        if (block instanceof CarvedPumpkinBlock) {
            BlockPos blockpos = event.getPos();
            Level level = event.getEntity().getLevel();
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = createSteelGolemFull().find(level, blockpos);
            if (blockpattern$blockpatternmatch != null) {
                for (int j = 0; j < createSteelGolemFull().getWidth(); ++j) {
                    for (int k = 0; k < createSteelGolemFull().getHeight(); ++k) {
                        BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(j, k, 0);
                        level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                    }
                }
                blockpos = blockpattern$blockpatternmatch.getBlock(1, 2, 0).getPos();
                SteelGolem steelgolem = RegistryHandler.STEEL_GOLEM.get().create(level);
                steelgolem.setPlayerCreated(true);
                steelgolem.moveTo((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                level.addFreshEntity(steelgolem);

                for (ServerPlayer serverplayer1 : level.getEntitiesOfClass(ServerPlayer.class, steelgolem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer1, steelgolem);
                }

                for (int i1 = 0; i1 < createSteelGolemFull().getWidth(); ++i1) {
                    for (int j1 = 0; j1 < createSteelGolemFull().getHeight(); ++j1) {
                        BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(i1, j1, 0);
                        level.blockUpdated(blockinworld.getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }
}
