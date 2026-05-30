package net.mindoth.skillcloaks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class SkillcloaksClient {

    public static void registerHandlers(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(SkillcloaksClient::clientSetup);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
    }
}
