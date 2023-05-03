package net.mindoth.skillcloaks.client;

import net.mindoth.skillcloaks.client.model.CloakModel;
import net.mindoth.skillcloaks.client.renderer.CloakRenderer;
import net.mindoth.skillcloaks.client.renderer.CurioRenderer;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class CurioRenderers {

    private static final Map<Item, CurioRenderer> renderers = new HashMap<>();

    public static CurioRenderer getRenderer(Item curio) {
        return renderers.get(curio);
    }

    public static void register() {
        //Cloak
        renderers.put(SkillcloaksItems.AGILITY_CLOAK.get(), new CloakRenderer("cloak/agility_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.ATTACK_CLOAK.get(), new CloakRenderer("cloak/attack_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.CONSTRUCTION_CLOAK.get(), new CloakRenderer("cloak/construction_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.DEFENCE_CLOAK.get(), new CloakRenderer("cloak/defence_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.FARMING_CLOAK.get(), new CloakRenderer("cloak/farming_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.FIREMAKING_CLOAK.get(), new CloakRenderer("cloak/firemaking_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.WOODCUTTING_CLOAK.get(), new CloakRenderer("cloak/woodcutting_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.MAGIC_CLOAK.get(), new CloakRenderer("cloak/magic_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.MINING_CLOAK.get(), new CloakRenderer("cloak/mining_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.RANGING_CLOAK.get(), new CloakRenderer("cloak/ranging_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.FISHING_CLOAK.get(), new CloakRenderer("cloak/fishing_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.COOKING_CLOAK.get(), new CloakRenderer("cloak/cooking_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.HUNTER_CLOAK.get(), new CloakRenderer("cloak/hunter_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.SLAYER_CLOAK.get(), new CloakRenderer("cloak/slayer_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.RUNECRAFT_CLOAK.get(), new CloakRenderer("cloak/runecraft_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.PRAYER_CLOAK.get(), new CloakRenderer("cloak/prayer_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.FLETCHING_CLOAK.get(), new CloakRenderer("cloak/fletching_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.SMITHING_CLOAK.get(), new CloakRenderer("cloak/smithing_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.HERBLORE_CLOAK.get(), new CloakRenderer("cloak/herblore_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.CRAFTING_CLOAK.get(), new CloakRenderer("cloak/crafting_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.HITPOINTS_CLOAK.get(), new CloakRenderer("cloak/hitpoints_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.STRENGTH_CLOAK.get(), new CloakRenderer("cloak/strength_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.THIEVING_CLOAK.get(), new CloakRenderer("cloak/thieving_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
        renderers.put(SkillcloaksItems.MAX_CLOAK.get(), new CloakRenderer("cloak/max_cloak", CloakModel.cloak(RenderType::entityCutoutNoCull)));
    }
}
