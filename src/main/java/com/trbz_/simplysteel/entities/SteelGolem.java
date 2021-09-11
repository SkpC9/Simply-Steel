package com.trbz_.simplysteel.entities;

import com.trbz_.simplysteel.SimplySteel;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SteelGolem extends IronGolem {

    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SteelGolem.class, EntityDataSerializers.BYTE);

    public static final EntityType<SteelGolem> TYPE = RegistryHandler.STEEL_GOLEM.get();

    public static final AttributeSupplier MAP = createAttributes().build();

    public SteelGolem(EntityType<? extends IronGolem> p_28834_, Level p_28835_) {
        super(p_28834_, p_28835_);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 150.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 15.0D).add(Attributes.ARMOR_TOUGHNESS, 4.0D);
    }

    protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
        ItemStack itemstack = p_28861_.getItemInHand(p_28862_);
        if (!itemstack.is(RegistryHandler.STEEL_INGOT.get())) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(37.5F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, f1);
                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                if (!p_28861_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public boolean isPlayerCreated() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setPlayerCreated(boolean p_28888_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_28888_) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
        }

    }

}
