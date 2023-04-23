package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.client.curio.CurioLayers;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.network.message.SkillCloaksNetwork;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(SkillCloaks.MOD_ID)
public class SkillCloaks {
    public static final String MOD_ID = "skillcloaks";

    public SkillCloaks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            SkillCloaksClient.registerHandlers();
        }

        addRegistries(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SkillCloaksCommonConfig.SPEC, "skillcloaks-common.toml");
    }

    private void addRegistries(final IEventBus modEventBus) {
        SkillCloaksItems.REGISTRY.register(modEventBus);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onRegisterLayerDefinitions);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
    }

    @SubscribeEvent
    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {SlotTypePreset.BACK };
        for (SlotTypePreset type : types) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        //InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("cloak").icon(new ResourceLocation("curios:slot/slot_cloak")).build());
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        SkillCloaksNetwork.init();
    }
}
