package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.network.message.SkillcloaksNetwork;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraftforge.api.distmarker.Dist;
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

@Mod(Skillcloaks.MOD_ID)
public class Skillcloaks {
    public static final String MOD_ID = "skillcloaks";

    public Skillcloaks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            SkillcloaksClient.registerHandlers();
        }
        addRegistries(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SkillcloaksCommonConfig.SPEC, "skillcloaks-common.toml");
    }

    private void addRegistries(final IEventBus modEventBus) {
        SkillcloaksItems.REGISTRY.register(modEventBus);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::commonSetup);
    }

    @SubscribeEvent
    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = { SlotTypePreset.BACK };
        for (SlotTypePreset type : types) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        //InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("cloak").icon(PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE).build());
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        SkillcloaksNetwork.init();
    }
}
