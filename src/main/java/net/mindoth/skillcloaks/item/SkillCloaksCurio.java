package net.mindoth.skillcloaks.item;

import net.minecraft.world.item.Item;

public class SkillcloaksCurio extends Item {

    public SkillcloaksCurio(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    public SkillcloaksCurio() {
        this(new Properties());
    }
}