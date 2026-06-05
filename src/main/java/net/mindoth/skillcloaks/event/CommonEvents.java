package net.mindoth.skillcloaks.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if ( ModCommonConfig.SACK_TRADES.get() ) {
            if (event.getType() == VillagerProfession.FLETCHER || event.getType() == VillagerProfession.TOOLSMITH) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.BROWN_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemCost(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.FARMER || event.getType() == VillagerProfession.FISHERMAN) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.GREEN_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemCost(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.ARMORER || event.getType() == VillagerProfession.WEAPONSMITH) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.RED_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemCost(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.LIBRARIAN || event.getType() == VillagerProfession.CLERIC) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.BLUE_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemCost(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
        }
    }

    @SubscribeEvent
    public static void getBlackSack(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if ( ModCommonConfig.BLACK_SACK_TRADE.get() ) {
            if ( !event.getLevel().isClientSide ) {
                if ( event.getTarget() instanceof Villager) {
                    Villager target = (Villager) event.getTarget();
                    if ( target.getVillagerData().getProfession() == VillagerProfession.NITWIT ) {
                        if ( player.getItemInHand(event.getHand()).getItem() == Items.EMERALD && player.getItemInHand(event.getHand()).getCount() >= 64 ) {
                            player.getItemInHand(event.getHand()).shrink(64);
                            ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, new ItemStack(ModItems.BLACK_SACK.get()));
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
