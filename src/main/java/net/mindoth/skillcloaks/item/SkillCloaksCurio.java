package net.mindoth.skillcloaks.item;

import net.mindoth.skillcloaks.itemgroup.SkillCloaksItemGroup;
import net.minecraft.world.item.Item;

public class SkillCloaksCurio extends Item {

    public SkillCloaksCurio(Properties properties) {
        super(properties.stacksTo(1).tab(SkillCloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant());
    }

    public SkillCloaksCurio() {
        this(new Properties());
    }
}