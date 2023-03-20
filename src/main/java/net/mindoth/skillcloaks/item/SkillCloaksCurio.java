package net.mindoth.skillcloaks.item;

import net.minecraft.world.item.Item;

public class SkillCloaksCurio extends Item {

    public SkillCloaksCurio(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    public SkillCloaksCurio() {
        this(new Properties());
    }
}