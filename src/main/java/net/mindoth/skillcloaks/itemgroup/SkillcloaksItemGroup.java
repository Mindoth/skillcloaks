package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SkillcloaksItemGroup extends ItemGroup {

    public static final SkillcloaksItemGroup SKILL_CLOAKS_TAB = new SkillcloaksItemGroup(ItemGroup.TABS.length, "skill_cloaks_tab");


    public SkillcloaksItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(SkillcloaksItems.ATTACK_CLOAK.get());
    }
}
