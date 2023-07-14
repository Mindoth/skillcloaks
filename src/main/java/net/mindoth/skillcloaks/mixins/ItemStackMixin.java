package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "hurtAndBreak", at = @At("HEAD"), cancellable = true)
    public <T extends LivingEntity> void hurtAndBreak(int pAmount, T pEntity, Consumer<T> pOnBroken, CallbackInfo callback) {
        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() ) {
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.ATTACK_CLOAK.get(), pEntity).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), pEntity).isPresent() ) {
                double r = new Random().nextDouble();
                if ( this.getItem() instanceof ArmorItem && r <= SkillcloaksCommonConfig.ARMOR_DURABILITY_CHANCE.get() && SkillcloaksCommonConfig.ARMOR_DURABILITY_CHANCE.get() > 0.0 ) {
                    callback.cancel();
                }
            }
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.RUNECRAFT_CLOAK.get(), pEntity).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), pEntity).isPresent() ) {
                double r = new Random().nextDouble();
                if ( !(this.getItem() instanceof ArmorItem) && r <= SkillcloaksCommonConfig.TOOL_DURABILITY_CHANCE.get() && SkillcloaksCommonConfig.TOOL_DURABILITY_CHANCE.get() > 0.0 ) {
                    callback.cancel();
                }
            }
        }
    }

    @Shadow public abstract Item getItem();
}
