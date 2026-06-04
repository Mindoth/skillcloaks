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

    public static final ModConfigSpec SPEC = BUILDER.build();
}
