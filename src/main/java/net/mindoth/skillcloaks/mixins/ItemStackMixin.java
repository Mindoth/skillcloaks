package net.mindoth.skillcloaks.mixins;

import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() ) {
            if (CuriosApi.getCuriosHelper().findFirstCurio(pEntity, SkillCloaksItems.ATTACK_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(pEntity, SkillCloaksItems.MAX_CLOAK.get()).isPresent()) {
                Random r = new Random();
                if (this.getItem() instanceof ArmorItem && r.nextDouble() < SkillCloaksCommonConfig.ARMOR_DURABILITY_CHANCE.get()) {
                    callback.cancel();
                }
            }
            if (CuriosApi.getCuriosHelper().findFirstCurio(pEntity, SkillCloaksItems.RUNECRAFT_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(pEntity, SkillCloaksItems.MAX_CLOAK.get()).isPresent()) {
                Random r = new Random();
                if (!(this.getItem() instanceof ArmorItem) && r.nextDouble() < SkillCloaksCommonConfig.TOOL_DURABILITY_CHANCE.get()) {
                    callback.cancel();
                }
            }
        }
    }

    @Shadow
    public abstract Item getItem();
}
