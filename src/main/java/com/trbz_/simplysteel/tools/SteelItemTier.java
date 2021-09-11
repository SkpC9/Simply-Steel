package com.trbz_.simplysteel.tools;

import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum SteelItemTier implements Tier {
    STEEL(2, 484, 6.5F, 2.0F, 16, () -> {
        return Ingredient.of(RegistryHandler.STEEL_INGOT.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    private SteelItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = repairMaterialIn;
    }

    @Override
    public int getUses() { return this.maxUses; }

    @Override
    public float getSpeed() { return this.efficiency; }

    @Override
    public float getAttackDamageBonus() { return this.attackDamage; }

    @Override
    public int getLevel() { return this.harvestLevel; }

    @Override
    public int getEnchantmentValue() { return this.enchantability; }

    @Override
    public Ingredient getRepairIngredient() { return this.repairMaterial.get(); }
}
