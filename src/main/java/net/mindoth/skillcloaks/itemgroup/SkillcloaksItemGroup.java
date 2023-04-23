package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SkillCloaksItemGroup extends CreativeModeTab {

    public static final SkillCloaksItemGroup SKILL_CLOAKS_TAB = new SkillCloaksItemGroup(CreativeModeTab.TABS.length, "skill_cloaks_tab");

    public SkillCloaksItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(SkillCloaksItems.ATTACK_CLOAK.get());
    }
}
