package net.mindoth.skillcloaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillcloaksCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> COSMETIC_ONLY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SACK_TRADES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BLACK_SACK_TRADE;
    public static final ForgeConfigSpec.ConfigValue<Integer> CLOAK_ARMOR;
    public static final ForgeConfigSpec.ConfigValue<Double> SLAYER_THRESHOLD;
    public static final ForgeConfigSpec.ConfigValue<Double> ARROW_RETURN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> ARMOR_DURABILITY_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> TOOL_DURABILITY_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> FARMING_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Double> THIEVING_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Integer> FISHING_LUCK;
    public static final ForgeConfigSpec.ConfigValue<Integer> MINING_LUCK;
    public static final ForgeConfigSpec.ConfigValue<Integer> STRENGTH_KNOCKBACK_RESISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> FIREMAKING_TORCH;
    public static final ForgeConfigSpec.ConfigValue<Double> FIREMAKING_STICK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LUCENT_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Integer> DEFENCE_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Double> AGILITY_REDUCER;

    static {
        BUILDER.push("Configs for Skillcloaks");

        COSMETIC_ONLY = BUILDER.comment("true = Cloaks are cosmetic only. false = Cloaks have abilities (Default = false)")
                .define("Cosmetic only", false);

        SACK_TRADES = BUILDER.comment("Will villagers have a chance to offer a sack in a trade (Default = true)")
                .define("Sack trades", true);

        BLACK_SACK_TRADE = BUILDER.comment("Will the Nitwit give the Black Sack if offered a stack of Emeralds? (Default = true)")
                .define("Black Sack trade", true);

        CLOAK_ARMOR = BUILDER.comment("How much armor will the cloaks give to the wearer. Only values greater than 0 will have any effect (Default = 0)")
                .define("Cloak armor", 0);

        SLAYER_THRESHOLD = BUILDER.comment("The percentage of health the target has to have left for Slayer Cloak to activate (Default = 0.25 = 25%)")
                .defineInRange("Slayer threshold", 0.25, 0.0, 1.0);

        ARROW_RETURN_CHANCE = BUILDER.comment("The percent chance for the Ranging Cloak or the Fletching Cloak to return an arrow (Default = 0.5 = 50%)")
                .defineInRange("Arrow return chance", 0.5, 0.0, 1.0);

        ARMOR_DURABILITY_CHANCE = BUILDER.comment("The percent chance for the Attack Cloak to prevent armor durability loss (Default = 0.5 = 50%)")
                .defineInRange("Armor durability loss prevent chance", 0.5, 0.0, 1.0);

        TOOL_DURABILITY_CHANCE = BUILDER.comment("The percent chance for the Runecraft Cloak to prevent tool durability loss (Default = 0.5 = 50%)")
                .defineInRange("Tool durability loss prevent chance", 0.5, 0.0, 1.0);

        FARMING_RANGE = BUILDER.comment("The bonus range in blocks your bonemeal reaches while wearing the Farming Cloak (Default = 2 = 3x3 area)")
                .define("Number of blocks your bonemeal area is extended", 2);

        THIEVING_MULTIPLIER = BUILDER.comment("The amount your visibility value is multiplied by while wearing the Thieving Cloak. Lower values makes you less likely to be spotted by enemies (Default = 0.2 = 20%)")
                .defineInRange("Thieving cloak visibility multiplier", 0.2, 0.0, 1.0);

        FISHING_LUCK = BUILDER.comment("The Amount of luck the Fishing Cloak gives (Default = 3)")
                .define("Fishing luck", 3);

        MINING_LUCK = BUILDER.comment("The Amount of fortune the Mining Cloak gives (Default = 1)")
                .define("Mining luck", 1);

        STRENGTH_KNOCKBACK_RESISTANCE = BUILDER.comment("The Amount of knockback resistance the Strength Cloak gives (Default = 1)")
                .define("Strength knockback resistance", 10);

        FIREMAKING_TORCH = BUILDER.comment("Should the Firemaking Cloak allow you to place down torches with sticks? (Default = true)")
                .define("Firemaking torch", true);

        FIREMAKING_STICK_CHANCE = BUILDER.comment("The chance for a stick to be used when placing down a torch with the Firemaking Cloak (Default = 1.0 = 100%)")
                .defineInRange("Firemaking stick chance", 1.0, 0.0, 1.0);

        LUCENT_COMPAT = BUILDER.comment("Should wearing the Firemaking Cloak give light if Lucent is installed (Default = true)")
                .define("Lucent compat", true);

        DEFENCE_COOLDOWN = BUILDER.comment("The amount of time in ticks for the cooldown of Defence Cloak (Default = 24000)")
                .define("Defence cooldown", 24000);

        AGILITY_REDUCER = BUILDER.comment("The amount your gained exhaustion is multiplied by when wearing the Agility Cloak. Lower numbers mean you get less exhaustion (Default = 0.5 = 50%)")
                .defineInRange("Agility exhaustion reducer", 0.5, 0.0, 1.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
