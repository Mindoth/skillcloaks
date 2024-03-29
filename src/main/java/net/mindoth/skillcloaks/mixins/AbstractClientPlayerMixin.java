package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;


@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(method = "isCapeLoaded", at = @At("HEAD"), cancellable = true)
    private void hideCape(CallbackInfoReturnable<Boolean> callback) {
        if ( CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.AGILITY_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.ATTACK_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.CONSTRUCTION_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.DEFENCE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FARMING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FIREMAKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FISHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HUNTER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MAGIC_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MINING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.COOKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.RANGING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.SLAYER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.RUNECRAFT_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.PRAYER_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FLETCHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HERBLORE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.CRAFTING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HITPOINTS_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.STRENGTH_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.THIEVING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.WOODCUTTING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MAX_CLOAK.get()).isPresent()
        ) {
            callback.setReturnValue(false);
        }
    }

    @Shadow protected abstract PlayerInfo getPlayerInfo();

    @Inject(method = "getElytraTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectElytraTexture(CallbackInfoReturnable<ResourceLocation> cir)
    {
        findElytra(cir);
    }

    private static final ResourceLocation ATTACK_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/attack_elytra.png");
    private static final ResourceLocation AGILITY_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/agility_elytra.png");
    private static final ResourceLocation CONSTRUCTION_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/construction_elytra.png");
    private static final ResourceLocation DEFENCE_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/defence_elytra.png");
    private static final ResourceLocation FARMING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/farming_elytra.png");
    private static final ResourceLocation FIREMAKING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/firemaking_elytra.png");
    private static final ResourceLocation WOODCUTTING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/woodcutting_elytra.png");
    private static final ResourceLocation MAGIC_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/magic_elytra.png");
    private static final ResourceLocation MINING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/mining_elytra.png");
    private static final ResourceLocation RANGING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/ranging_elytra.png");
    private static final ResourceLocation FISHING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/fishing_elytra.png");
    private static final ResourceLocation COOKING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/cooking_elytra.png");
    private static final ResourceLocation HUNTER_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/hunter_elytra.png");
    private static final ResourceLocation SLAYER_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/slayer_elytra.png");
    private static final ResourceLocation RUNECRAFT_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/runecraft_elytra.png");
    private static final ResourceLocation PRAYER_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/prayer_elytra.png");
    private static final ResourceLocation FLETCHING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/fletching_elytra.png");
    private static final ResourceLocation SMITHING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/smithing_elytra.png");
    private static final ResourceLocation HERBLORE_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/herblore_elytra.png");
    private static final ResourceLocation CRAFTING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/crafting_elytra.png");
    private static final ResourceLocation HITPOINTS_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/hitpoints_elytra.png");
    private static final ResourceLocation STRENGTH_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/strength_elytra.png");
    private static final ResourceLocation THIEVING_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/thieving_elytra.png");
    private static final ResourceLocation MAX_ELYTRA = new ResourceLocation(Skillcloaks.MOD_ID + ":textures/entity/curio/cloak/max_elytra.png");

    private void findElytra(CallbackInfoReturnable<ResourceLocation> cir) {
        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.AGILITY_CLOAK.get())
                .map(t -> AGILITY_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.ATTACK_CLOAK.get())
                .map(t -> ATTACK_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.CONSTRUCTION_CLOAK.get())
                .map(t -> CONSTRUCTION_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.DEFENCE_CLOAK.get())
                .map(t -> DEFENCE_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FARMING_CLOAK.get())
                .map(t -> FARMING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FIREMAKING_CLOAK.get())
                .map(t -> FIREMAKING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FISHING_CLOAK.get())
                .map(t -> FISHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HUNTER_CLOAK.get())
                .map(t -> HUNTER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MAGIC_CLOAK.get())
                .map(t -> MAGIC_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MINING_CLOAK.get())
                .map(t -> MINING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.COOKING_CLOAK.get())
                .map(t -> COOKING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.RANGING_CLOAK.get())
                .map(t -> RANGING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.WOODCUTTING_CLOAK.get())
                .map(t -> WOODCUTTING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.SLAYER_CLOAK.get())
                .map(t -> SLAYER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.RUNECRAFT_CLOAK.get())
                .map(t -> RUNECRAFT_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.PRAYER_CLOAK.get())
                .map(t -> PRAYER_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.FLETCHING_CLOAK.get())
                .map(t -> FLETCHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.SMITHING_CLOAK.get())
                .map(t -> SMITHING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HERBLORE_CLOAK.get())
                .map(t -> HERBLORE_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.CRAFTING_CLOAK.get())
                .map(t -> CRAFTING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.HITPOINTS_CLOAK.get())
                .map(t -> HITPOINTS_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.STRENGTH_CLOAK.get())
                .map(t -> STRENGTH_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.THIEVING_CLOAK.get())
                .map(t -> THIEVING_ELYTRA).ifPresent(cir::setReturnValue);

        CuriosApi.getCuriosHelper().findFirstCurio((AbstractClientPlayer) (Object) this, SkillcloaksItems.MAX_CLOAK.get())
                .map(t -> MAX_ELYTRA).ifPresent(cir::setReturnValue);
    }
}
