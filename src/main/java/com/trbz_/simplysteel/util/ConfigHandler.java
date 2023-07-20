package com.trbz_.simplysteel.util;


import com.trbz_.simplysteel.SimplySteel;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = SimplySteel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {

    public static final ForgeConfigSpec SERVER_SPEC;

    static{
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(BUILDER);
        SERVER_SPEC= BUILDER.build();
    }

    private static ForgeConfigSpec.IntValue STEEL_ITEM_HARVEST_LEVEL;
    private static ForgeConfigSpec.IntValue STEEL_ITEM_DURABILITY;
    private static ForgeConfigSpec.DoubleValue STEEL_ITEM_EFFICIENCY;
    private static ForgeConfigSpec.IntValue STEEL_ITEM_ENCHANTABILITY;
    private static ForgeConfigSpec.IntValue STEEL_SWORD_DAMAGE;
    private static ForgeConfigSpec.DoubleValue STEEL_SWORD_ATTACK_SPEED;
    private static ForgeConfigSpec.DoubleValue STEEL_AXE_DAMAGE;
    private static ForgeConfigSpec.DoubleValue STEEL_AXE_ATTACK_SPEED;
    private static ForgeConfigSpec.IntValue STEEL_SHEARS_DURABILITY;

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Steel Tools And Weapons");

        STEEL_ITEM_HARVEST_LEVEL= builder
                .comment("Wood:0 Stone:1 Iron:2 Diamond:3 Netherite:4")
                .defineInRange("steel_item_harvest_level", 2, 0, 4);

        STEEL_ITEM_DURABILITY = builder
                .defineInRange("steel_item_durability", 484, 1, 10000);
        STEEL_ITEM_EFFICIENCY = builder
                .comment("Wood:2 Stone:4 Iron:6 Diamond:8 Netherite:9 Gold:12")
                .defineInRange("steel_item_efficiency", 6.5D, 1D, 100D);
        STEEL_ITEM_ENCHANTABILITY = builder
                .comment("Wood:15 Stone:5 Iron:14 Diamond:10 Netherite:15 Gold:22")
                .defineInRange("steel_item_enchantability", 16, 1, 100);

        STEEL_SWORD_DAMAGE = builder
                .defineInRange("steel_sword_damage", 6, 1, 1000);

        STEEL_SWORD_ATTACK_SPEED = builder
                .defineInRange("steel_sword_attack_speed", 1.6D, 0.1D, 4D);
        STEEL_AXE_DAMAGE = builder
                .defineInRange("steel_axe_damage", 9D, 1D, 1000D);

        STEEL_AXE_ATTACK_SPEED = builder
                .defineInRange("steel_axe_attack_speed", 0.9D, 0.1D, 4D);

        builder.push("FireStarters");


        builder.pop();

        builder.push("Steel Shears");
        STEEL_SHEARS_DURABILITY = builder
                .defineInRange("steel_shears_durability", 460, 1, 10000);;

        builder.pop();

        builder.pop();
    }

    public static int steel_item_harvest_level;
    public static int steel_item_durability;
    public static float steel_item_efficiency;
    public static int steel_item_enchantability;
    public static int steel_sword_damage;
    public static float steel_sword_attack_speed;
    public static float steel_axe_damage;
    public static float steel_axe_attack_speed;
    public static int steel_shears_durability;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        steel_item_harvest_level = STEEL_ITEM_HARVEST_LEVEL.get();
        steel_item_durability = STEEL_ITEM_DURABILITY.get();
        steel_item_efficiency = STEEL_ITEM_EFFICIENCY.get().floatValue();
        steel_item_enchantability = STEEL_ITEM_ENCHANTABILITY.get();
        steel_sword_damage = STEEL_SWORD_DAMAGE.get();
        steel_sword_attack_speed = STEEL_SWORD_ATTACK_SPEED.get().floatValue();
        steel_axe_damage = STEEL_AXE_DAMAGE.get().floatValue();
        steel_axe_attack_speed = STEEL_AXE_ATTACK_SPEED.get().floatValue();
        steel_shears_durability = STEEL_SHEARS_DURABILITY.get();
    }
}