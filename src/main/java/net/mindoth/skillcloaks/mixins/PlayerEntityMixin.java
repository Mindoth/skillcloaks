package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    public void causeFoodExhaustion(float pExhaustion, CallbackInfo callback) {
        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() ) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            if ( !player.level.isClientSide ) {
                if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.AGILITY_CLOAK.get(), (PlayerEntity) (Object) this).isPresent()
                        || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), (PlayerEntity) (Object) this).isPresent() ) {
                    callback.cancel();
                    if ( !player.abilities.invulnerable ) {
                        pExhaustion *= SkillcloaksCommonConfig.AGILITY_REDUCER.get();
                        player.getFoodData().addExhaustion(pExhaustion);
                    }
                }
            }
        }
    }
}
