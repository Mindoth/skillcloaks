package net.mindoth.skillcloaks;

import com.mojang.blaze3d.platform.InputConstants;
import net.mindoth.skillcloaks.client.renderer.CurioRenderers;
import net.mindoth.skillcloaks.network.CloakAbilityPacket;
import net.mindoth.skillcloaks.network.ModNetwork;
import net.mindoth.skillcloaks.registries.KeyBinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public class SkillcloaksClient {

    public static void registerHandlers(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(SkillcloaksClient::clientSetup);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        CurioRenderers.register();
    }

    @EventBusSubscriber(modid = Skillcloaks.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            if ( mc.level == null ) return;
            onInput(mc, event.getKey(), event.getAction());
        }

        private static void onInput(Minecraft mc, int key, int action) {
            if (mc.screen == null && KeyBinds.CLOAK_ABILITY.isDown()) {
                PacketDistributor.sendToServer(new CloakAbilityPacket(key));
            }
        }
    }

    @SuppressWarnings("removal")
    @EventBusSubscriber(modid = Skillcloaks.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.CLOAK_ABILITY);
        }
    }
}
