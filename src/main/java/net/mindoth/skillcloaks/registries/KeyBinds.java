package net.mindoth.skillcloaks.registries;

import net.mindoth.skillcloaks.Skillcloaks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class KeyBinds {
    public static KeyBinding cloakAbility;

    public static void register(final FMLClientSetupEvent event) {
        cloakAbility = create("cloak_ability", KeyEvent.VK_U);

        ClientRegistry.registerKeyBinding(cloakAbility);
    }

    private static KeyBinding create(String name, int key) {
        return new KeyBinding("key." + Skillcloaks.MOD_ID + "." + name, key, "key.category." + Skillcloaks.MOD_ID);
    }
}
