package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SkillcloaksItemGroup extends CreativeModeTab {

    public static final SkillcloaksItemGroup SKILL_CLOAKS_TAB = new SkillcloaksItemGroup(CreativeModeTab.TABS.length, "skill_cloaks_tab");

    public SkillcloaksItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(SkillcloaksItems.ATTACK_CLOAK.get());
    }
}
