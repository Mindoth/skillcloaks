package net.mindoth.skillcloaks.registries;

import net.mindoth.skillcloaks.SkillCloaks;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class KeyBinds {
    public static KeyMapping cloakAbility;

    public static void register(final FMLClientSetupEvent event) {
        cloakAbility = create("cloak_ability", KeyEvent.VK_U);

        ClientRegistry.registerKeyBinding(cloakAbility);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + SkillCloaks.MOD_ID + "." + name, key, "key.category." + SkillCloaks.MOD_ID);
    }
}
