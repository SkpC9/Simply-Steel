// config method based on https://github.com/ewewukek/mc-musketmod/
package com.trbz_.simplysteel.util;

import com.trbz_.simplysteel.SimplySteel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Locale;
import java.util.Scanner;

public class ConfigHandler {
    private static final Logger logger = LogManager.getLogger(SimplySteel.class);
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    //    public static final String VERSION = "${mod_version}";
    public static final ComparableVersion VERSION = new ComparableVersion("2.3.0");
    // values exposed to other classes
    public static int steel_item_harvest_level;
    public static int steel_item_durability;
    public static float steel_item_efficiency;
    public static int steel_item_enchantability;
    public static int steel_sword_damage;
    public static float steel_sword_attack_speed;
    public static float steel_axe_damage;
    public static float steel_axe_attack_speed;
    public static int steel_shears_durability;
    public static int quartz_and_steel_durability;

    public static int steel_armor_maxDamageFactor;
    public static int steel_armor_enchantability;
    public static int steel_armor_helmet_armor;
    public static int steel_armor_chestplate_armor;
    public static int steel_armor_leggings_armor;
    public static int steel_armor_boots_armor;
    public static float steel_armor_toughness;
    public static float steel_armor_knockbackResistance;


    public static double steel_golem_max_health;
    public static double steel_golem_attack_damage;
    public static float steel_golem_use_ingot_heal;
    public static double steel_golem_movement_speed;


    public static void reload() {
        INSTANCE.setDefaults();
        INSTANCE.load();

        logger.info("Configuration has been loaded");
    }

    private void setDefaults() {
        steel_item_harvest_level = 2;
        steel_item_durability = 484;
        steel_item_efficiency = 6.5F;
        steel_item_enchantability = 16;
        steel_sword_damage = 6;
        steel_sword_attack_speed = 1.6F;
        steel_axe_damage = 9F;
        steel_axe_attack_speed = 0.9F;
        steel_shears_durability = 460;
        quartz_and_steel_durability = 88;

        steel_armor_maxDamageFactor = 20;
        steel_armor_enchantability = 11;
        steel_armor_helmet_armor = 2;
        steel_armor_chestplate_armor = 6;
        steel_armor_leggings_armor = 5;
        steel_armor_boots_armor = 2;
        steel_armor_toughness = 1F;
        steel_armor_knockbackResistance = 0F;


        steel_golem_max_health = 150;
        steel_golem_attack_damage = 15;
        steel_golem_use_ingot_heal = 37.5F;
        steel_golem_movement_speed = 0.25;
    }

    private void load() {
//        String version = "0";
        ComparableVersion version = new ComparableVersion("0");
        try (BufferedReader reader = Files.newBufferedReader(SimplySteel.CONFIG_PATH)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // to ignore comments
                int commentStart = line.indexOf('#');
                if (commentStart != -1) line = line.substring(0, commentStart);
                commentStart = line.indexOf('[');
                if (commentStart != -1) line = line.substring(0, commentStart);

                line.trim();
                if (line.length() == 0) continue;

                String errorPrefix = SimplySteel.CONFIG_PATH + ": line " + lineNumber + ": ";
                try (Scanner s = new Scanner(line)) {
                    s.useLocale(Locale.US);
                    s.useDelimiter("\\s*=\\s*");

                    if (!s.hasNext()) {
                        logger.warn(errorPrefix + "parameter name is missing");
                        continue;
                    }
                    String key = s.next().trim();
                    // use string version
                    if (key.equals("version")) {
                        if (!s.hasNext()) {
                            logger.warn(errorPrefix + "version number is missing");
                            continue;
                        }
                        version.parseVersion(s.next().trim());
                        continue;
                    }

                    if (!s.hasNextDouble()) {
                        logger.warn(errorPrefix + "value is missing/wrong/not a number");
                        continue;
                    }
                    double value = s.nextDouble();

                    switch (key) {
                        case "steel_item_harvest_level":
                            steel_item_harvest_level = (int) value;
                            break;
                        case "steel_item_durability":
                            steel_item_durability = (int) value;
                            break;
                        case "steel_item_efficiency":
                            steel_item_efficiency = (float) value;
                            break;
                        case "steel_item_enchantability":
                            steel_item_enchantability = (int) value;
                            break;
                        case "steel_sword_damage":
                            steel_sword_damage = (int) value;
                            break;
                        case "steel_sword_attack_speed":
                            steel_sword_attack_speed = (float) value;
                            break;
                        case "steel_axe_damage":
                            steel_axe_damage = (float) value;
                            break;
                        case "steel_axe_attack_speed":
                            steel_axe_attack_speed = (float) value;
                            break;
                        case "steel_shears_durability":
                            steel_shears_durability = (int) value;
                            break;
                        case "quartz_and_steel_durability":
                            quartz_and_steel_durability = (int) value;
                            break;
                        case "steel_armor_maxDamageFactor":
                            steel_armor_maxDamageFactor = (int) value;
                            break;
                        case "steel_armor_enchantability":
                            steel_armor_enchantability = (int) value;
                            break;
                        case "steel_armor_helmet_armor":
                            steel_armor_helmet_armor = (int) value;
                            break;
                        case "steel_armor_chestplate_armor":
                            steel_armor_chestplate_armor = (int) value;
                            break;
                        case "steel_armor_leggings_armor":
                            steel_armor_leggings_armor = (int) value;
                            break;
                        case "steel_armor_boots_armor":
                            steel_armor_boots_armor = (int) value;
                            break;
                        case "steel_armor_toughness":
                            steel_armor_toughness = (float) value;
                            break;
                        case "steel_armor_knockbackResistance":
                            steel_armor_knockbackResistance = (float) value;
                            break;
                        case "steel_golem_max_health":
                            steel_golem_max_health = value;
                            break;
                        case "steel_golem_attack_damage":
                            steel_golem_attack_damage = value;
                            break;
                        case "steel_golem_use_ingot_heal":
                            steel_golem_use_ingot_heal = (float) value;
                            break;
                        case "steel_golem_movement_speed":
                            steel_golem_movement_speed = value;
                            break;
                        default:
                            logger.warn(errorPrefix + "unrecognized parameter name: " + key);
                    }
                }
            }
        } catch (NoSuchFileException e) {
            save();
            logger.info("Configuration file not found, default created");

        } catch (IOException e) {
            logger.warn("Could not read configuration file: ", e);
        }
        // may save twice, but not big deal
        if (version.compareTo(VERSION) < 0) {
            logger.info("Configuration file belongs to older version, updating");
            save();
        }
    }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(SimplySteel.CONFIG_PATH)) {
            writer.write("version = " + VERSION + "\n");
            writer.write("# Note: Please restart minecraft to apply changes in config");
            writer.write("\n");
            writer.write("\n");
            writer.write("[Steel Tools And Weapons]\n");
            writer.write("\n");
            writer.write("# Wood:0 Stone:1 Iron:2 Diamond:3 Netherite:4\n");
            writer.write("# Default:2\n");
            writer.write("steel_item_harvest_level = " + steel_item_harvest_level + "\n");
            writer.write("\n");
            writer.write("# Default:484\n");
            writer.write("steel_item_durability = " + steel_item_durability + "\n");
            writer.write("\n");
            writer.write("# Wood:2 Stone:4 Iron:6 Diamond:8 Netherite:9 Gold:12\n");
            writer.write("# Default:6.5\n");
            writer.write("steel_item_efficiency = " + steel_item_efficiency + "\n");
            writer.write("\n");
            writer.write("# Wood:15 Stone:5 Iron:14 Diamond:10 Netherite:15 Gold:22\n");
            writer.write("# Default:16\n");
            writer.write("steel_item_enchantability = " + steel_item_enchantability + "\n");
            writer.write("\n");
            writer.write("# Default:6\n");
            writer.write("steel_sword_damage = " + steel_sword_damage + "\n");
            writer.write("\n");
            writer.write("# Default:1.6\n");
            writer.write("steel_sword_attack_speed = " + steel_sword_attack_speed + "\n");
            writer.write("\n");
            writer.write("# Default:9\n");
            writer.write("steel_axe_damage = " + steel_axe_damage + "\n");
            writer.write("\n");
            writer.write("# Default:0.9\n");
            writer.write("steel_axe_attack_speed = " + steel_axe_attack_speed + "\n");
            writer.write("\n");
            writer.write("# Default:460\n");
            writer.write("steel_shears_durability = " + steel_shears_durability + "\n");
            writer.write("\n");
            writer.write("# Default:88\n");
            writer.write("quartz_and_steel_durability = " + quartz_and_steel_durability + "\n");
            writer.write("\n");
            writer.write("\n");
            writer.write("[Steel Armors]\n");
            writer.write("\n");
            writer.write("# Determines durability\n");
            writer.write("# Leather:5 Chain:15 Iron:15 Diamond:33 Netherite:37 Gold:7\n");
            writer.write("# Default:20\n");
            writer.write("steel_armor_maxDamageFactor = " + steel_armor_maxDamageFactor + "\n");
            writer.write("\n");
            writer.write("# Leather:15 Chain:12 Iron:9 Diamond:10 Netherite:15 Gold:25\n");
            writer.write("# Default:11\n");
            writer.write("steel_armor_enchantability = " + steel_armor_enchantability + "\n");
            writer.write("\n");
            writer.write("# Leather:1 Chain:2 Iron:2 Diamond:3 Netherite:3 Gold:2\n");
            writer.write("# Default:2\n");
            writer.write("steel_armor_helmet_armor = " + steel_armor_helmet_armor + "\n");
            writer.write("\n");
            writer.write("# Leather:3 Chain:5 Iron:6 Diamond:8 Netherite:8 Gold:5\n");
            writer.write("# Default:6\n");
            writer.write("steel_armor_chestplate_armor = " + steel_armor_chestplate_armor + "\n");
            writer.write("\n");
            writer.write("# Leather:2 Chain:4 Iron:5 Diamond:6 Netherite:6 Gold:3\n");
            writer.write("# Default:5\n");
            writer.write("steel_armor_leggings_armor = " + steel_armor_leggings_armor + "\n");
            writer.write("\n");
            writer.write("# Leather:1 Chain:1 Iron:2 Diamond:3 Netherite:3 Gold:1\n");
            writer.write("# Default:2\n");
            writer.write("steel_armor_boots_armor = " + steel_armor_boots_armor + "\n");
            writer.write("\n");
            writer.write("# Leather:0 Chain:0 Iron:0 Diamond:2 Netherite:3 Gold:0\n");
            writer.write("# Default:1\n");
            writer.write("steel_armor_toughness = " + steel_armor_toughness + "\n");
            writer.write("\n");
            writer.write("# Leather:0 Chain:0 Iron:0 Diamond:0 Netherite:0.1 Gold:0\n");
            writer.write("# Default:0\n");
            writer.write("steel_armor_knockbackResistance = " + steel_armor_knockbackResistance + "\n");
            writer.write("\n");
            writer.write("\n");
            writer.write("[Steel Golem]\n");
            writer.write("\n");
            writer.write("# Max health is capped at 1024 by minecraft\n");
            writer.write("# Default:150\n");
            writer.write("steel_golem_max_health = " + steel_golem_max_health + "\n");
            writer.write("\n");
            writer.write("# Default:15\n");
            writer.write("steel_golem_attack_damage = " + steel_golem_attack_damage + "\n");
            writer.write("\n");
            writer.write("# Using steel ingots to steel golem heals it by this value\n");
            writer.write("# Default:37.5\n");
            writer.write("steel_golem_use_ingot_heal = " + steel_golem_use_ingot_heal + "\n");
            writer.write("\n");
            writer.write("# Movement speed seems only apply to newly created golem after changing config\n");
            writer.write("# Default:0.25\n");
            writer.write("steel_golem_movement_speed = " + steel_golem_movement_speed + "\n");
            writer.write("\n");

        } catch (IOException e) {
            logger.warn("Could not save configuration file: ", e);
        }
    }
}