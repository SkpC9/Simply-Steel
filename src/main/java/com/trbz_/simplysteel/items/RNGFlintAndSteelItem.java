package com.trbz_.simplysteel.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Random;

public class RNGFlintAndSteelItem extends Item {

   float successChance;

   public RNGFlintAndSteelItem(Properties builder, float successChanceIn) {
      super(builder);
      successChance = successChanceIn;
   }

   /**
    * Called when this item is used when targetting a Block
    */

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      Level level = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      BlockState blockstate = level.getBlockState(blockpos);
      Random random = new Random();
      float strikingFloat = random.nextFloat();
      boolean strikingSuccess = successChance >= strikingFloat;

      if (CampfireBlock.canLight(blockstate) || CandleBlock.canLight(blockstate) || CandleCakeBlock.canLight(blockstate)) {

         level.playSound(player, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.2F - 0.4F * strikingFloat);

         //client just pretends it works until server says something to fix randomness desync
         if(level.isClientSide()) return InteractionResult.SUCCESS;

         if(strikingSuccess) {
            level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
         }
         if (player != null) {
            context.getItemInHand().hurtAndBreak(1, player, (p_219999_1_) -> {
               p_219999_1_.broadcastBreakEvent(context.getHand());
            });
         }

         return InteractionResult.SUCCESS;
      } else {
         BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
         if (BaseFireBlock.canBePlacedAt(level, blockpos1, context.getHorizontalDirection())) {

            level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.2F - 0.4F * strikingFloat);

            //client just pretends it works until server says something to fix randomness desync
            if(level.isClientSide()) return InteractionResult.SUCCESS;

            if(strikingSuccess) {
               BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
               level.setBlock(blockpos1, blockstate1, 11);
            }
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
               if(strikingSuccess) {
                  CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos1, itemstack);
               }
               itemstack.hurtAndBreak(1, player, (p_219998_1_) -> {
                  p_219998_1_.broadcastBreakEvent(context.getHand());
               });
            }

            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.FAIL;
         }
      }
   }
}
