package net.mindoth.skillcloaks.itemgroup;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillcloaksItemGroup {

    public static CreativeModeTab SKILL_CLOAKS_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        SKILL_CLOAKS_TAB = event.registerCreativeModeTab(new ResourceLocation(Skillcloaks.MOD_ID, "skill_cloaks_tab"),
                builder -> builder.icon(() -> new ItemStack(SkillcloaksItems.ATTACK_CLOAK.get())).title(Component.literal("Skillcloaks")).build());
    }
}
