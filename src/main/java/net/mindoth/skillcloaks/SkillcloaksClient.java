package net.mindoth.skillcloaks;

import net.mindoth.skillcloaks.client.curio.CurioRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
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
}
