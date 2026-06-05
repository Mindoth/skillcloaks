package net.mindoth.skillcloaks.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModCommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Boolean> COSMETIC_ONLY = BUILDER
            .comment("true = Cloaks are cosmetic only. false = Cloaks have abilities (Default = false)")
            .define("Cosmetic only", false);

    public static final ModConfigSpec.ConfigValue<Integer> SKILL_CLOAK_ARMOR = BUILDER
            .comment("How much armor will the cloaks give to the wearer. Only values greater than 0 will have any effect (Default = 0)")
            .define("Skillcloak armor", 0);

    public static final ModConfigSpec.ConfigValue<Double> AGILITY_REDUCER = BUILDER
            .comment("The amount your gained exhaustion is multiplied by when wearing the Agility Cloak. Lower numbers mean you get less exhaustion (Default = 0.5 = 50%)")
            .defineInRange("Agility exhaustion reducer", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.ConfigValue<Double> ARMOR_DURABILITY_CHANCE = BUILDER
            .comment("The percent chance for the Attack Cloak to prevent armor durability loss (Default = 0.5 = 50%)")
            .defineInRange("Armor durability loss prevent chance", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.ConfigValue<Double> TOOL_DURABILITY_CHANCE = BUILDER
            .comment("The percent chance for the Runecraft Cloak to prevent tool durability loss (Default = 0.5 = 50%)")
            .defineInRange("Tool durability loss prevent chance", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.ConfigValue<Integer> DEFENCE_COOLDOWN = BUILDER.comment("The amount of time in ticks for the cooldown of Defence Cloak (Default = 24000)")
            .define("Defence cooldown", 24000);

    public static final ModConfigSpec.ConfigValue<Integer> FARMING_RANGE = BUILDER.comment("The bonus range in blocks your bonemeal reaches while wearing the Farming Cloak (Default = 2 = 3x3 area)")
            .define("Number of blocks your bonemeal area is extended", 2);

    public static final ModConfigSpec.ConfigValue<Boolean> FIREMAKING_TORCH = BUILDER.comment("Should the Firemaking Cloak allow you to place down torches with sticks? (Default = true)")
            .define("Firemaking torch", true);

    public static final ModConfigSpec.ConfigValue<Integer> FIREMAKING_STICK_CHANCE = BUILDER.comment("The chance for a stick to be used when placing down a torch with the Firemaking Cloak (Default = 100 = 100%)")
            .defineInRange("Firemaking stick chance", 100, 0, 100);

    public static final ModConfigSpec.ConfigValue<Integer> FISHING_LUCK = BUILDER.comment("The Amount of luck the Fishing Cloak gives (Default = 3)")
            .define("Fishing luck", 3);

    public static final ModConfigSpec.ConfigValue<Double> ARROW_RETURN_CHANCE = BUILDER.comment("The percent chance for the Ranging Cloak or the Fletching Cloak to return an arrow (Default = 0.5 = 50%)")
            .defineInRange("Arrow return chance", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.ConfigValue<Integer> STRENGTH_KNOCKBACK_RESISTANCE = BUILDER.comment("The Amount of knockback resistance the Strength Cloak gives (Default = 1)")
            .define("Strength knockback resistance", 1);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
