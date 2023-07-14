package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    public void causeFoodExhaustion(float pExhaustion, CallbackInfo callback) {
        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() ) {
            Player player = (Player) (Object) this;
            if ( !player.level.isClientSide ) {
                if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.AGILITY_CLOAK.get(), (Player) (Object) this).isPresent()
                        || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), (Player) (Object) this).isPresent() ) {
                    callback.cancel();
                    if ( !player.getAbilities().invulnerable ) {
                        pExhaustion *= SkillcloaksCommonConfig.AGILITY_REDUCER.get();
                        player.getFoodData().addExhaustion(pExhaustion);
                    }
                }
            }
        }
    }
}
