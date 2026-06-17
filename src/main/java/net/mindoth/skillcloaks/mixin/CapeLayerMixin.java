package net.mindoth.skillcloaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @SuppressWarnings("ALL")
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void hideCape(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
        if ( CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.AGILITY_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.ATTACK_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.CONSTRUCTION_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.DEFENCE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.FARMING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.FIREMAKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.FISHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.HUNTER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.MAGIC_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.MINING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.COOKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.RANGING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.SLAYER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.RUNECRAFT_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.PRAYER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.FLETCHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.HERBLORE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.CRAFTING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.HITPOINTS_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.STRENGTH_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.THIEVING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.WOODCUTTING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, ModItems.MAX_CLOAK.get()).isPresent()
        ) {
            callback.cancel();
        }
    }
}
