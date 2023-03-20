package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;


@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Inject(method = "isCapeLoaded", at = @At("HEAD"), cancellable = true)
    private boolean hideCape(CallbackInfoReturnable callback) {
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.AGILITY_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.ATTACK_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.CONSTRUCTION_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.COOKING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.CRAFTING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.DEFENCE_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FARMING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FIREMAKING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FISHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FLETCHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HERBLORE_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HITPOINTS_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HUNTER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAGIC_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MINING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.PRAYER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.RANGING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.RUNECRAFT_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SLAYER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SMITHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.STRENGTH_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.THIEVING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.WOODCUTTING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this).isPresent()
        ) {
            callback.cancel();
            return false;
        }
        else {
            return this.getPlayerInfo() != null;
        }
    }

    @Shadow protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getElytraTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectElytraTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        findElytra(cir);
    }

    private static final ResourceLocation AGILITY_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/agility_elytra.png");
    private static final ResourceLocation ATTACK_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/attack_elytra.png");
    private static final ResourceLocation CONSTRUCTION_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/construction_elytra.png");
    private static final ResourceLocation COOKING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/cooking_elytra.png");
    private static final ResourceLocation CRAFTING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/crafting_elytra.png");
    private static final ResourceLocation DEFENCE_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/defence_elytra.png");
    private static final ResourceLocation FARMING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/farming_elytra.png");
    private static final ResourceLocation FIREMAKING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/firemaking_elytra.png");
    private static final ResourceLocation FISHING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/fishing_elytra.png");
    private static final ResourceLocation FLETCHING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/fletching_elytra.png");
    private static final ResourceLocation HERBLORE_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/herblore_elytra.png");
    private static final ResourceLocation HITPOINTS_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/hitpoints_elytra.png");
    private static final ResourceLocation HUNTER_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/hunter_elytra.png");
    private static final ResourceLocation MAGIC_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/magic_elytra.png");
    private static final ResourceLocation MINING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/mining_elytra.png");
    private static final ResourceLocation PRAYER_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/prayer_elytra.png");
    private static final ResourceLocation RANGING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/ranging_elytra.png");
    private static final ResourceLocation RUNECRAFT_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/runecraft_elytra.png");
    private static final ResourceLocation SLAYER_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/slayer_elytra.png");
    private static final ResourceLocation SMITHING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/smithing_elytra.png");
    private static final ResourceLocation STRENGTH_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/strength_elytra.png");
    private static final ResourceLocation THIEVING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/thieving_elytra.png");
    private static final ResourceLocation WOODCUTTING_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/woodcutting_elytra.png");
    private static final ResourceLocation MAX_ELYTRA = new ResourceLocation(SkillCloaks.MOD_ID + ":textures/entity/curio/cloak/max_elytra.png");

    private void findElytra(CallbackInfoReturnable<ResourceLocation> cir) {

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.AGILITY_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> AGILITY_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.ATTACK_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> ATTACK_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.CONSTRUCTION_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> CONSTRUCTION_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.COOKING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> COOKING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.CRAFTING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> CRAFTING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.DEFENCE_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> DEFENCE_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FARMING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> FARMING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FIREMAKING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> FIREMAKING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FISHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> FISHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FLETCHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> FLETCHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HERBLORE_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> HERBLORE_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HITPOINTS_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> HITPOINTS_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.HUNTER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> HUNTER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAGIC_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> MAGIC_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MINING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> MINING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.PRAYER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> PRAYER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.RANGING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> RANGING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.RUNECRAFT_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> RUNECRAFT_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SLAYER_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> SLAYER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SMITHING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> SMITHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.STRENGTH_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> STRENGTH_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.THIEVING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> THIEVING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.WOODCUTTING_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> WOODCUTTING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t -> MAX_ELYTRA).ifPresent(cir::setReturnValue);
    }
}
