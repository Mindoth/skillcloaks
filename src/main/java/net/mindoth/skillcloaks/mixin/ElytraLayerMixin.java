package net.mindoth.skillcloaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private ResourceLocation cloakElytraTexture(ResourceLocation oldTexture, PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation returnTexture = oldTexture;
        if ( livingEntity instanceof AbstractClientPlayer player ) {
            ResourceLocation newTexture = elytraTexture(player);
            if ( newTexture != null ) returnTexture = newTexture;
        }
        return returnTexture;
    }

    @SuppressWarnings("ALL")
    private ResourceLocation elytraTexture(AbstractClientPlayer player) {
        ResourceLocation texture = null;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.AGILITY_CLOAK.get()).isPresent() ) texture = AGILITY_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.ATTACK_CLOAK.get()).isPresent() ) texture = ATTACK_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.CONSTRUCTION_CLOAK.get()).isPresent() ) texture = CONSTRUCTION_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.DEFENCE_CLOAK.get()).isPresent() ) texture = DEFENCE_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FARMING_CLOAK.get()).isPresent() ) texture = FARMING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FIREMAKING_CLOAK.get()).isPresent() ) texture = FIREMAKING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.WOODCUTTING_CLOAK.get()).isPresent() ) texture = WOODCUTTING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAGIC_HOOD.get()).isPresent() ) texture = MAGIC_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MINING_CLOAK.get()).isPresent() ) texture = MINING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.RANGING_CLOAK.get()).isPresent() ) texture = RANGING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FISHING_CLOAK.get()).isPresent() ) texture = FISHING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.COOKING_CLOAK.get()).isPresent() ) texture = COOKING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HUNTER_CLOAK.get()).isPresent() ) texture = HUNTER_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SLAYER_CLOAK.get()).isPresent() ) texture = SLAYER_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.RUNECRAFT_CLOAK.get()).isPresent() ) texture = RUNECRAFT_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.PRAYER_HOOD.get()).isPresent() ) texture = PRAYER_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FLETCHING_CLOAK.get()).isPresent() ) texture = FLETCHING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SMITHING_CLOAK.get()).isPresent() ) texture = SMITHING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HERBLORE_CLOAK.get()).isPresent() ) texture = HERBLORE_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.CRAFTING_CLOAK.get()).isPresent() ) texture = CRAFTING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HITPOINTS_CLOAK.get()).isPresent() ) texture = HITPOINTS_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.STRENGTH_CLOAK.get()).isPresent() ) texture = STRENGTH_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.THIEVING_CLOAK.get()).isPresent() ) texture = THIEVING_ELYTRA;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) texture = MAX_ELYTRA;
        return texture;
    }

    private static final ResourceLocation AGILITY_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/agility_elytra.png");
    private static final ResourceLocation ATTACK_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/attack_elytra.png");
    private static final ResourceLocation CONSTRUCTION_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/construction_elytra.png");
    private static final ResourceLocation DEFENCE_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/defence_elytra.png");
    private static final ResourceLocation FARMING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/farming_elytra.png");
    private static final ResourceLocation FIREMAKING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/firemaking_elytra.png");
    private static final ResourceLocation WOODCUTTING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/woodcutting_elytra.png");
    private static final ResourceLocation MAGIC_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/magic_elytra.png");
    private static final ResourceLocation MINING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/mining_elytra.png");
    private static final ResourceLocation RANGING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/ranging_elytra.png");
    private static final ResourceLocation FISHING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/fishing_elytra.png");
    private static final ResourceLocation COOKING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/cooking_elytra.png");
    private static final ResourceLocation HUNTER_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/hunter_elytra.png");
    private static final ResourceLocation SLAYER_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/slayer_elytra.png");
    private static final ResourceLocation RUNECRAFT_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/runecraft_elytra.png");
    private static final ResourceLocation PRAYER_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/prayer_elytra.png");
    private static final ResourceLocation FLETCHING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/fletching_elytra.png");
    private static final ResourceLocation SMITHING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/smithing_elytra.png");
    private static final ResourceLocation HERBLORE_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/herblore_elytra.png");
    private static final ResourceLocation CRAFTING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/crafting_elytra.png");
    private static final ResourceLocation HITPOINTS_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/hitpoints_elytra.png");
    private static final ResourceLocation STRENGTH_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/strength_elytra.png");
    private static final ResourceLocation THIEVING_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/thieving_elytra.png");
    private static final ResourceLocation MAX_ELYTRA = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/entity/curio/cloak/max_elytra.png");
}
