package net.mindoth.skillcloaks.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModCommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Boolean> COSMETIC_ONLY = BUILDER
            .comment("true = Cloaks are cosmetic only. false = Cloaks have abilities (Default = false)")
            .define("Cosmetic only", false);

    public static final ModConfigSpec.ConfigValue<Integer> SKILL_CLOAK_ARMOR = BUILDER
            .comment("How much armor will the cloaks give to the wearer. Only values greater than 0 will have any effect (Default = 0)")
            .define("Skill Cloak armor", 0);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
