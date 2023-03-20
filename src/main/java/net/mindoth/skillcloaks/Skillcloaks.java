package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.client.curio.CurioLayers;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.itemgroup.SkillcloaksItemGroup;
import net.mindoth.skillcloaks.network.message.SkillcloaksNetwork;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
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
        modEventBus.addListener(this::onRegisterLayerDefinitions);
        modEventBus.addListener(this::addCreative);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == SkillcloaksItemGroup.SKILL_CLOAKS_TAB) {
            event.accept(SkillcloaksItems.AGILITY_CLOAK);
            event.accept(SkillcloaksItems.ATTACK_CLOAK);
            event.accept(SkillcloaksItems.CONSTRUCTION_CLOAK);
            event.accept(SkillcloaksItems.COOKING_CLOAK);
            event.accept(SkillcloaksItems.CRAFTING_CLOAK);
            event.accept(SkillcloaksItems.DEFENCE_CLOAK);
            event.accept(SkillcloaksItems.FARMING_CLOAK);
            event.accept(SkillcloaksItems.FIREMAKING_CLOAK);
            event.accept(SkillcloaksItems.FISHING_CLOAK);
            event.accept(SkillcloaksItems.FLETCHING_CLOAK);
            event.accept(SkillcloaksItems.HERBLORE_CLOAK);
            event.accept(SkillcloaksItems.HITPOINTS_CLOAK);
            event.accept(SkillcloaksItems.HUNTER_CLOAK);
            event.accept(SkillcloaksItems.MAGIC_CLOAK);
            event.accept(SkillcloaksItems.MAX_CLOAK);
            event.accept(SkillcloaksItems.MINING_CLOAK);
            event.accept(SkillcloaksItems.PRAYER_CLOAK);
            event.accept(SkillcloaksItems.RANGING_CLOAK);
            event.accept(SkillcloaksItems.RUNECRAFT_CLOAK);
            event.accept(SkillcloaksItems.SLAYER_CLOAK);
            event.accept(SkillcloaksItems.SMITHING_CLOAK);
            event.accept(SkillcloaksItems.STRENGTH_CLOAK);
            event.accept(SkillcloaksItems.THIEVING_CLOAK);
            event.accept(SkillcloaksItems.WOODCUTTING_CLOAK);
            event.accept(SkillcloaksItems.AGILITY_HOOD);
            event.accept(SkillcloaksItems.ATTACK_HOOD);
            event.accept(SkillcloaksItems.CONSTRUCTION_HOOD);
            event.accept(SkillcloaksItems.COOKING_HOOD);
            event.accept(SkillcloaksItems.CRAFTING_HOOD);
            event.accept(SkillcloaksItems.DEFENCE_HOOD);
            event.accept(SkillcloaksItems.FARMING_HOOD);
            event.accept(SkillcloaksItems.FIREMAKING_HOOD);
            event.accept(SkillcloaksItems.FISHING_HOOD);
            event.accept(SkillcloaksItems.FLETCHING_HOOD);
            event.accept(SkillcloaksItems.HERBLORE_HOOD);
            event.accept(SkillcloaksItems.HITPOINTS_HOOD);
            event.accept(SkillcloaksItems.HUNTER_HOOD);
            event.accept(SkillcloaksItems.MAGIC_HOOD);
            event.accept(SkillcloaksItems.MAX_HOOD);
            event.accept(SkillcloaksItems.MINING_HOOD);
            event.accept(SkillcloaksItems.PRAYER_HOOD);
            event.accept(SkillcloaksItems.RANGING_HOOD);
            event.accept(SkillcloaksItems.RUNECRAFT_HOOD);
            event.accept(SkillcloaksItems.SLAYER_HOOD);
            event.accept(SkillcloaksItems.SMITHING_HOOD);
            event.accept(SkillcloaksItems.STRENGTH_HOOD);
            event.accept(SkillcloaksItems.THIEVING_HOOD);
            event.accept(SkillcloaksItems.WOODCUTTING_HOOD);
            event.accept(SkillcloaksItems.BLACK_SACK);
            event.accept(SkillcloaksItems.BLUE_SACK);
            event.accept(SkillcloaksItems.BROWN_SACK);
            event.accept(SkillcloaksItems.GREEN_SACK);
            event.accept(SkillcloaksItems.RED_SACK);
        }
    }

    @SubscribeEvent
    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = { SlotTypePreset.BACK };
        for (SlotTypePreset type : types) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        //InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("cloak").icon(new ResourceLocation("curios:slot/slot_cloak")).build());
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        SkillcloaksNetwork.init();
    }
}
