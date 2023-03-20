package net.mindoth.skillcloaks.event;

import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.network.message.SkillCloaksNetwork;
import net.mindoth.skillcloaks.network.message.CloakAbilityPacket;
import net.mindoth.skillcloaks.registries.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null && KeyBinds.cloakAbility.isDown()) {
            SkillCloaksNetwork.CHANNEL.sendToServer(new CloakAbilityPacket(key));
        }
    }
}
