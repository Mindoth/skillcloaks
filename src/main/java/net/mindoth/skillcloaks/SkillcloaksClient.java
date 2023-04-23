package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.client.curio.CurioRenderers;
import net.mindoth.skillcloaks.network.message.CloakAbilityPacket;
import net.mindoth.skillcloaks.network.message.SkillcloaksNetwork;
import net.mindoth.skillcloaks.registries.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SkillcloaksClient {

    public static void registerHandlers() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(SkillcloaksClient::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        CurioRenderers.register();
    }

    @Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;
            onInput(mc, event.getKey(), event.getAction());
        }

        private static void onInput(Minecraft mc, int key, int action) {
            if (mc.screen == null && KeyBinds.CLOAK_ABILITY.isDown()) {
                SkillcloaksNetwork.CHANNEL.sendToServer(new CloakAbilityPacket(key));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.CLOAK_ABILITY);
        }
    }
}
