package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;

import static net.minecraft.world.item.CrossbowItem.getChargeDuration;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FletchingCloakItem extends CurioItem {

    public FletchingCloakItem(String name) {
        super(name);
    }

    @SubscribeEvent
    public static void onCrossbowUseFinish(final LivingEntityUseItemEvent.Stop event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getEntity() instanceof Player player ) {
            if ( !player.level().isClientSide ) {
                ItemStack pStack = event.getItem();
                boolean flag = player.getAbilities().instabuild
                        || (EnchantmentHelper.hasAnyEnchantments(pStack) && EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.INFINITY, pStack) > 0);
                Item crossbow = pStack.getItem();
                ItemStack pAmmoStack = player.getProjectile(pStack);

                if ( !pAmmoStack.isEmpty() || flag ) {
                    if (pAmmoStack.isEmpty()) {
                        pAmmoStack = new ItemStack(Items.ARROW);
                    }

                    if ( crossbow instanceof CrossbowItem ) {
                        int pCount = event.getDuration();
                        float f = (float) (pStack.getUseDuration(player) - pCount) / (float) getChargeDuration(pStack, player);
                        if (f >= 1.0f) {
                            boolean flag1 = player.getAbilities().instabuild || (pAmmoStack.getItem() instanceof ArrowItem && ((ArrowItem) pAmmoStack.getItem()).isInfinite(pAmmoStack, pStack, player));
                            if (!flag1 && !player.getAbilities().instabuild) {

                                double randomValue = new Random().nextDouble();

                                if ( ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FLETCHING_CLOAK.get()).isPresent() 
                                        || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() )
                                        && randomValue <= ModCommonConfig.ARROW_RETURN_CHANCE.get() && ModCommonConfig.ARROW_RETURN_CHANCE.get() > 0.0 ) {
                                    ItemStack returnArrow = new ItemStack(pAmmoStack.getItem(), 1);
                                    setPotionContents(returnArrow, pAmmoStack);
                                    ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, returnArrow);
                                    drop.setDeltaMovement(0, 0, 0);
                                    drop.setNoPickUpDelay();
                                    player.level().addFreshEntity(drop);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void setPotionContents(ItemStack returnArrow, ItemStack pAmmoStack) {
        returnArrow.set(DataComponents.POTION_CONTENTS, pAmmoStack.get(DataComponents.POTION_CONTENTS));
    }
}
