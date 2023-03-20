package net.mindoth.skillcloaks.client.curio;

import net.mindoth.skillcloaks.client.curio.CurioLayers;
import net.mindoth.skillcloaks.client.model.CloakModel;
import net.mindoth.skillcloaks.client.renderer.CloakRenderer;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {

    public static void register() {
        //Cloak
        CuriosRendererRegistry.register(SkillCloaksItems.AGILITY_CLOAK.get(), () -> new CloakRenderer("cloak/agility_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.ATTACK_CLOAK.get(), () -> new CloakRenderer("cloak/attack_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.CONSTRUCTION_CLOAK.get(), () -> new CloakRenderer("cloak/construction_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.DEFENCE_CLOAK.get(), () -> new CloakRenderer("cloak/defence_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.FARMING_CLOAK.get(), () -> new CloakRenderer("cloak/farming_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.FIREMAKING_CLOAK.get(), () -> new CloakRenderer("cloak/firemaking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.WOODCUTTING_CLOAK.get(), () -> new CloakRenderer("cloak/woodcutting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.MAGIC_CLOAK.get(), () -> new CloakRenderer("cloak/magic_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.MINING_CLOAK.get(), () -> new CloakRenderer("cloak/mining_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.RANGING_CLOAK.get(), () -> new CloakRenderer("cloak/ranging_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.FISHING_CLOAK.get(), () -> new CloakRenderer("cloak/fishing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.COOKING_CLOAK.get(), () -> new CloakRenderer("cloak/cooking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.HUNTER_CLOAK.get(), () -> new CloakRenderer("cloak/hunter_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.SLAYER_CLOAK.get(), () -> new CloakRenderer("cloak/slayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.RUNECRAFT_CLOAK.get(), () -> new CloakRenderer("cloak/runecraft_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.PRAYER_CLOAK.get(), () -> new CloakRenderer("cloak/prayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.FLETCHING_CLOAK.get(), () -> new CloakRenderer("cloak/fletching_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.SMITHING_CLOAK.get(), () -> new CloakRenderer("cloak/smithing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.HERBLORE_CLOAK.get(), () -> new CloakRenderer("cloak/herblore_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.CRAFTING_CLOAK.get(), () -> new CloakRenderer("cloak/crafting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.HITPOINTS_CLOAK.get(), () -> new CloakRenderer("cloak/hitpoints_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.STRENGTH_CLOAK.get(), () -> new CloakRenderer("cloak/strength_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.THIEVING_CLOAK.get(), () -> new CloakRenderer("cloak/thieving_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillCloaksItems.MAX_CLOAK.get(), () -> new CloakRenderer("cloak/max_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
