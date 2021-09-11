package com.trbz_.simplysteel.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.BaseFireBlock;
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
      Player playerentity = context.getPlayer();
      Level world = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      BlockState blockstate = world.getBlockState(blockpos);
      Random random = new Random();
      float strikingFloat = random.nextFloat();
      boolean strikingSuccess = successChance >= strikingFloat;

      if (CampfireBlock.canLight(blockstate)) {

         world.playSound(playerentity, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.2F - 0.4F * strikingFloat);

         //client just pretends it works until server says something to fix randomness desync
         if(world.isClientSide()) return InteractionResult.SUCCESS;

         if(strikingSuccess) {
            world.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
         }
         if (playerentity != null) {
            context.getItemInHand().hurtAndBreak(1, playerentity, (p_219999_1_) -> {
               p_219999_1_.broadcastBreakEvent(context.getHand());
            });
         }

         return InteractionResult.SUCCESS;
      } else {
         BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
         if (BaseFireBlock.canBePlacedAt(world, blockpos1, context.getHorizontalDirection())) {

            world.playSound(playerentity, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.2F - 0.4F * strikingFloat);

            //client just pretends it works until server says something to fix randomness desync
            if(world.isClientSide()) return InteractionResult.SUCCESS;

            if(strikingSuccess) {
               BlockState blockstate1 = BaseFireBlock.getState(world, blockpos1);
               world.setBlock(blockpos1, blockstate1, 11);
            }
            ItemStack itemstack = context.getItemInHand();
            if (playerentity instanceof ServerPlayer) {
               if(strikingSuccess) {
                  CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerentity, blockpos1, itemstack);
               }
               itemstack.hurtAndBreak(1, playerentity, (p_219998_1_) -> {
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
