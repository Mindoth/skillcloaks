package net.mindoth.skillcloaks.mixin;

import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
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

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "hurtAndBreak", at = @At("HEAD"), cancellable = true)
    public void hurtAndBreak(int pAmount, ServerLevel level, @Nullable ServerPlayer pEntity, Consumer<Item> consumer, CallbackInfo callback) {
        if ( !ModCommonConfig.COSMETIC_ONLY.get() ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(pEntity, ModItems.ATTACK_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(pEntity, ModItems.MAX_CLOAK.get()).isPresent() ) {
                double r = new Random().nextDouble();
                if ( this.getItem() instanceof ArmorItem && r <= ModCommonConfig.ARMOR_DURABILITY_CHANCE.get() && ModCommonConfig.ARMOR_DURABILITY_CHANCE.get() > 0.0 ) {
                    callback.cancel();
                }
            }
            if ( CuriosApi.getCuriosHelper().findFirstCurio(pEntity, ModItems.RUNECRAFT_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(pEntity, ModItems.MAX_CLOAK.get()).isPresent() ) {
                double r = new Random().nextDouble();
                if ( !(this.getItem() instanceof ArmorItem) && r <= ModCommonConfig.TOOL_DURABILITY_CHANCE.get() && ModCommonConfig.TOOL_DURABILITY_CHANCE.get() > 0.0 ) {
                    callback.cancel();
                }
            }
        }
    }

    @Shadow
    public abstract Item getItem();
}
