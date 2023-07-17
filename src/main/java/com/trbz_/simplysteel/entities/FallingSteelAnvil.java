package com.trbz_.simplysteel.entities;

import java.util.function.Predicate;

import com.trbz_.simplysteel.blocks.SteelAnvilBlock;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FallingSteelAnvil extends FallingBlockEntity {
    private float breakChanceMultiplier = 1.0f;

    public FallingSteelAnvil(EntityType<? extends FallingSteelAnvil> entityType, Level level) {
        super(entityType, level);
    }

    public FallingSteelAnvil(Level level, double x, double y, double z, BlockState blockState, float bcm) {
        this(RegistryHandler.FALLING_ANVIL.get(), level);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
        this.breakChanceMultiplier = bcm;
    }

    public boolean causeFallDamage(float p_149643_, float p_149644_, DamageSource p_149645_) {
        int i = Mth.ceil(p_149643_ - 1.0F);
        if (i < 0) {
            return false;
        } else {
            Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
            Block $$8 = this.blockState.getBlock();
            DamageSource damagesource1;
            if ($$8 instanceof Fallable) {
                Fallable fallable = (Fallable)$$8;
                damagesource1 = fallable.getFallDamageSource(this);
            } else {
                damagesource1 = this.damageSources().fallingBlock(this);
            }

            DamageSource damagesource = damagesource1;
            float f = (float)Math.min(Mth.floor((float)i * this.fallDamagePerDistance), this.fallDamageMax);
            this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
                p_149649_.hurt(damagesource, f);
            });
            boolean flag = this.blockState.is(BlockTags.ANVIL);
            // breakChanceMultiplier is 0.5f in SteelAnvilBlock
            if (flag && f > 0.0F && this.random.nextFloat() < (0.05F + (float)i * 0.05F) * breakChanceMultiplier) {
                BlockState blockstate = AnvilBlock.damage(this.blockState);
                if (blockstate == null) {
                    this.cancelDrop = true;
                } else {
                    this.blockState = blockstate;
                }
            }

            return false;
        }
    }
}