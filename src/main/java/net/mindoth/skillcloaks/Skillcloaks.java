package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.client.renderer.CurioLayers;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.ModCreativeTab;
import net.mindoth.skillcloaks.item.armor.ModArmorMaterials;
import net.mindoth.skillcloaks.registries.ModData;
import net.mindoth.skillcloaks.registries.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(Skillcloaks.MOD_ID)
public class Skillcloaks {
    public static final String MOD_ID = "skillcloaks";

    public Skillcloaks(IEventBus modBus, ModContainer modContainer) {
        if ( FMLEnvironment.dist == Dist.CLIENT ) SkillcloaksClient.registerHandlers(modBus, modContainer);
        addRegistries(modBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC);
        modBus.addListener(this::onRegisterLayerDefinitions);
    }

    private void addRegistries(final IEventBus modBus) {
        ModCreativeTab.CREATIVE_MODE_TABS.register(modBus);
        ModData.DATA_COMPONENT_TYPES.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(modBus);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
    }
}