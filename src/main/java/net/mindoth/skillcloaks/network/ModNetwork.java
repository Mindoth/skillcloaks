package net.mindoth.skillcloaks.network;

import net.mindoth.skillcloaks.Skillcloaks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@SuppressWarnings("removal")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Skillcloaks.MOD_ID)
public class ModNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar payloadRegistrar = event.registrar(Skillcloaks.MOD_ID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(CloakAbilityPacket.TYPE, CloakAbilityPacket.STREAM_CODEC, CloakAbilityPacket::handle);
    }
}
