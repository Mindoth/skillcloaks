package net.mindoth.skillcloaks.item.cloak;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Set;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class MagicCloakItem extends CurioItem {

    public MagicCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerUseMagic(final PlayerInteractEvent.RightClickItem event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        Player player = event.getEntity();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAGIC_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
            ItemStack stack = event.getItemStack();
            if ( stack.isEnchanted() && player.isHolding(Items.BOOK) ) {
                if ( stack.isEnchanted() ) {
                    event.setCanceled(true);
                    Level level = player.level();
                    if ( !level.isClientSide() ) {
                        if ( player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == Items.BOOK ) {
                            player.getItemBySlot(EquipmentSlot.MAINHAND).shrink(1);
                        }
                        else {
                            player.getItemBySlot(EquipmentSlot.OFFHAND).shrink(1);
                        }
                        //Map enchantments
                        ItemEnchantments enchantments = stack.getTagEnchantments();
                        Set<Object2IntMap.Entry<Holder<Enchantment>>> entrySet = enchantments.entrySet();
                        int enchantmentLvl = entrySet.iterator().next().getIntValue();
                        ItemStack dropItem = new ItemStack(Items.ENCHANTED_BOOK);
                        Holder<Enchantment> enchantment = entrySet.iterator().next().getKey();
                        dropItem.enchant(enchantment, enchantmentLvl);

                        EnchantmentHelper.setEnchantments(stack, ItemEnchantments.EMPTY);
                        for ( Object2IntMap.Entry<Holder<Enchantment>> entry : entrySet ) {
                            if ( entry.getKey() != enchantment ) stack.enchant(entry.getKey(), entry.getIntValue());
                        }

                        //Drop as item entity
                        ItemEntity drop = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), dropItem);
                        drop.setDeltaMovement(0, 0, 0);
                        drop.setNoPickUpDelay();
                        level.addFreshEntity(drop);

                        //Sound
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1, 1);
                    }
                }
            }
        }
    }
}
