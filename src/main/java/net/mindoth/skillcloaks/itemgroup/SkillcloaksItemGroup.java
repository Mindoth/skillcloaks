package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SkillCloaksItemGroup extends ItemGroup {

    public static final SkillCloaksItemGroup SKILL_CLOAKS_TAB = new SkillCloaksItemGroup(ItemGroup.TABS.length, "skill_cloaks_tab");


    public SkillCloaksItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(SkillCloaksItems.ATTACK_CLOAK.get());
    }
}
