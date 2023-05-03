package net.mindoth.skillcloaks.item;

import net.mindoth.skillcloaks.itemgroup.SkillcloaksItemGroup;
import net.minecraft.world.item.Item;

public class SkillcloaksCurio extends Item {

    public SkillcloaksCurio(Properties properties) {
        super(properties.stacksTo(1).tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant());
    }

    public SkillcloaksCurio() {
        this(new Properties());
    }
}