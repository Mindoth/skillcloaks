package net.mindoth.skillcloaks.client.renderer;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.shadowizardlib.client.model.CloakModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.function.Supplier;

public class CurioLayers {

    public static final ModelLayerLocation
            SKILLCLOAK = createLayerLocation("skillcloak");

    public static ModelLayerLocation createLayerLocation(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, name), name);
    }

    private static Supplier<LayerDefinition> layer(MeshDefinition mesh, int textureWidth, int textureHeight) {
        return () -> LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    private static void register(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation layerLocation, Supplier<LayerDefinition> layer) {
        event.registerLayerDefinition(layerLocation, layer);
    }

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        register(event, SKILLCLOAK, layer(CloakModel.createCloak(), 64, 64));
    }
}
