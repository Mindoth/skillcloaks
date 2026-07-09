package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
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

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class RangingCloakItem extends CurioItem {
    
    public RangingCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onItemUseFinish(final LivingEntityUseItemEvent.Stop event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getEntity() instanceof Player player ) {
            if (!player.level().isClientSide) {
                ItemStack pStack = event.getItem();
                HolderLookup.RegistryLookup<Enchantment> lookup = player.level().registryAccess().lookup(Registries.ENCHANTMENT).get();
                int enchantLvl = EnchantmentHelper.getItemEnchantmentLevel(lookup.getOrThrow(Enchantments.INFINITY), pStack);
                boolean flag = player.getAbilities().instabuild || enchantLvl > 0;
                Item bow = pStack.getItem();
                ItemStack itemstack = player.getProjectile(pStack);

                if (!itemstack.isEmpty() || flag) {
                    if (itemstack.isEmpty()) {
                        itemstack = new ItemStack(Items.ARROW);
                    }

                    int i = 72000 - event.getDuration();
                    float f = BowItem.getPowerForTime(i);
                    if (bow instanceof BowItem) {
                        if (!((double) f < 0.1D)) {
                            boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, pStack, player));
                            if (!flag1 && !player.getAbilities().instabuild) {

                                double randomValue = new Random().nextDouble();

                                if ( !flag && ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.RANGING_CLOAK.get()).isPresent()
                                        || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() )
                                        && randomValue <= ModCommonConfig.ARROW_RETURN_CHANCE.get() && ModCommonConfig.ARROW_RETURN_CHANCE.get() > 0.0 ) {
                                    ItemStack returnArrow = new ItemStack(itemstack.getItem(), 1);
                                    setPotionContents(returnArrow, itemstack);
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
