package net.mindoth.skillcloaks.client.curio;

import net.mindoth.skillcloaks.client.model.CloakModel;
import net.mindoth.skillcloaks.client.renderer.CloakRenderer;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {

    public static void register() {
        //Cloak
        CuriosRendererRegistry.register(SkillcloaksItems.AGILITY_CLOAK.get(), () -> new CloakRenderer("cloak/agility_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.ATTACK_CLOAK.get(), () -> new CloakRenderer("cloak/attack_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.CONSTRUCTION_CLOAK.get(), () -> new CloakRenderer("cloak/construction_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.DEFENCE_CLOAK.get(), () -> new CloakRenderer("cloak/defence_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.FARMING_CLOAK.get(), () -> new CloakRenderer("cloak/farming_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.FIREMAKING_CLOAK.get(), () -> new CloakRenderer("cloak/firemaking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.WOODCUTTING_CLOAK.get(), () -> new CloakRenderer("cloak/woodcutting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.MAGIC_CLOAK.get(), () -> new CloakRenderer("cloak/magic_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.MINING_CLOAK.get(), () -> new CloakRenderer("cloak/mining_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.RANGING_CLOAK.get(), () -> new CloakRenderer("cloak/ranging_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.FISHING_CLOAK.get(), () -> new CloakRenderer("cloak/fishing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.COOKING_CLOAK.get(), () -> new CloakRenderer("cloak/cooking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.HUNTER_CLOAK.get(), () -> new CloakRenderer("cloak/hunter_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.SLAYER_CLOAK.get(), () -> new CloakRenderer("cloak/slayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.RUNECRAFT_CLOAK.get(), () -> new CloakRenderer("cloak/runecraft_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.PRAYER_CLOAK.get(), () -> new CloakRenderer("cloak/prayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.FLETCHING_CLOAK.get(), () -> new CloakRenderer("cloak/fletching_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.SMITHING_CLOAK.get(), () -> new CloakRenderer("cloak/smithing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.HERBLORE_CLOAK.get(), () -> new CloakRenderer("cloak/herblore_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.CRAFTING_CLOAK.get(), () -> new CloakRenderer("cloak/crafting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.HITPOINTS_CLOAK.get(), () -> new CloakRenderer("cloak/hitpoints_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.STRENGTH_CLOAK.get(), () -> new CloakRenderer("cloak/strength_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.THIEVING_CLOAK.get(), () -> new CloakRenderer("cloak/thieving_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(SkillcloaksItems.MAX_CLOAK.get(), () -> new CloakRenderer("cloak/max_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
