package net.mindoth.skillcloaks.registries;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {

    public static final String KEY_CLOAK_ABILITY = "key.skillcloaks.cloak_ability";
    public static final String KEY_CATEGORY_SKILLCLOAKS = "key.category.skillcloaks";
    public static final KeyMapping CLOAK_ABILITY = new KeyMapping(KEY_CLOAK_ABILITY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_U, KEY_CATEGORY_SKILLCLOAKS);
}
