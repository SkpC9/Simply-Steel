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
            Predicate<Entity> predicate;
            DamageSource damagesource;
            if (this.blockState.getBlock() instanceof Fallable) {
                Fallable fallable = (Fallable) this.blockState.getBlock();
                predicate = fallable.getHurtsEntitySelector();
                damagesource = fallable.getFallDamageSource();
            } else {
                predicate = EntitySelector.NO_SPECTATORS;
                damagesource = DamageSource.FALLING_BLOCK;
            }

            float f = (float) Math.min(Mth.floor((float) i * this.fallDamagePerDistance), this.fallDamageMax);
            this.level.getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
                p_149649_.hurt(damagesource, f);
            });
            boolean flag = this.blockState.is(BlockTags.ANVIL);
            if (flag && f > 0.0F && this.random.nextFloat() < (i + 1) * 0.05F * breakChanceMultiplier) {
                BlockState blockstate = SteelAnvilBlock.damage(this.blockState);
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