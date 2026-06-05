package net.mindoth.skillcloaks.client.renderer;

import net.mindoth.shadowizardlib.client.model.CloakModel;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {

    public static void register() {
        //Cloak
        CuriosRendererRegistry.register(ModItems.AGILITY_CLOAK.get(), () -> new CloakRenderer("cloak/agility_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.ATTACK_CLOAK.get(), () -> new CloakRenderer("cloak/attack_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.CONSTRUCTION_CLOAK.get(), () -> new CloakRenderer("cloak/construction_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.DEFENCE_CLOAK.get(), () -> new CloakRenderer("cloak/defence_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.FARMING_CLOAK.get(), () -> new CloakRenderer("cloak/farming_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.FIREMAKING_CLOAK.get(), () -> new CloakRenderer("cloak/firemaking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.WOODCUTTING_CLOAK.get(), () -> new CloakRenderer("cloak/woodcutting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.MAGIC_CLOAK.get(), () -> new CloakRenderer("cloak/magic_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.MINING_CLOAK.get(), () -> new CloakRenderer("cloak/mining_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.RANGING_CLOAK.get(), () -> new CloakRenderer("cloak/ranging_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.FISHING_CLOAK.get(), () -> new CloakRenderer("cloak/fishing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.COOKING_CLOAK.get(), () -> new CloakRenderer("cloak/cooking_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.HUNTER_CLOAK.get(), () -> new CloakRenderer("cloak/hunter_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.SLAYER_CLOAK.get(), () -> new CloakRenderer("cloak/slayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.RUNECRAFT_CLOAK.get(), () -> new CloakRenderer("cloak/runecraft_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.PRAYER_CLOAK.get(), () -> new CloakRenderer("cloak/prayer_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.FLETCHING_CLOAK.get(), () -> new CloakRenderer("cloak/fletching_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.SMITHING_CLOAK.get(), () -> new CloakRenderer("cloak/smithing_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.HERBLORE_CLOAK.get(), () -> new CloakRenderer("cloak/herblore_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.CRAFTING_CLOAK.get(), () -> new CloakRenderer("cloak/crafting_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.HITPOINTS_CLOAK.get(), () -> new CloakRenderer("cloak/hitpoints_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.STRENGTH_CLOAK.get(), () -> new CloakRenderer("cloak/strength_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.THIEVING_CLOAK.get(), () -> new CloakRenderer("cloak/thieving_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.MAX_CLOAK.get(), () -> new CloakRenderer("cloak/max_cloak", new CloakModel(bakeLayer(CurioLayers.SKILLCLOAK), RenderType::entityCutoutNoCull)));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
