package net.mindoth.skillcloaks.mixin;

import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Player.class)
public class PlayerMixin {

    @SuppressWarnings("ALL")
    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    public void causeFoodExhaustion(float pExhaustion, CallbackInfo callback) {
        if ( !ModCommonConfig.COSMETIC_ONLY.get() ) {
            Player player = (Player) (Object) this;
            if ( !player.level().isClientSide ) {
                if ( CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.AGILITY_CLOAK.get(), player).isPresent()
                        || CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.MAX_CLOAK.get(), player).isPresent() ) {
                    callback.cancel();
                    if ( !player.getAbilities().invulnerable ) {
                        pExhaustion *= ModCommonConfig.AGILITY_REDUCER.get();
                        player.getFoodData().addExhaustion(pExhaustion);
                    }
                }
            }
        }
    }
}
