package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SkillcloaksItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Skillcloaks.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKILL_CLOAKS_TAB = CREATIVE_MODE_TABS.register("skill_cloaks_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(SkillcloaksItems.ATTACK_CLOAK.get())).title(Component.translatable("creativemodetab.skillcloaks_tab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
