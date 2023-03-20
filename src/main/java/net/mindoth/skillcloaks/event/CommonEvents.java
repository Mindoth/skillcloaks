package net.mindoth.skillcloaks.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if ( SkillCloaksCommonConfig.SACK_TRADES.get() ) {
            if (event.getType() == VillagerProfession.FLETCHER || event.getType() == VillagerProfession.TOOLSMITH) {
                Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
                ItemStack stack = new ItemStack(SkillCloaksItems.BROWN_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.FARMER || event.getType() == VillagerProfession.FISHERMAN) {
                Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
                ItemStack stack = new ItemStack(SkillCloaksItems.GREEN_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.ARMORER || event.getType() == VillagerProfession.WEAPONSMITH) {
                Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
                ItemStack stack = new ItemStack(SkillCloaksItems.RED_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
            if (event.getType() == VillagerProfession.LIBRARIAN || event.getType() == VillagerProfession.CLERIC) {
                Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
                ItemStack stack = new ItemStack(SkillCloaksItems.BLUE_SACK.get(), 1);
                int villagerLevel = 5;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 64),
                        stack, 12, 30, 0.05F));
            }
        }
    }

    @SubscribeEvent
    public static void getBlackSack(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        if (SkillCloaksCommonConfig.SACK_TRADES.get()) {
            if (event.getTarget() instanceof VillagerEntity) {
                VillagerEntity target = (VillagerEntity) event.getTarget();
                if (target.getVillagerData().getProfession() == VillagerProfession.NITWIT) {
                    if (player.getItemInHand(event.getHand()).getItem() == Items.EMERALD && player.getItemInHand(event.getHand()).getCount() >= 64) {
                        player.getItemInHand(event.getHand()).shrink(64);
                        ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.BLACK_SACK.get()));
                        drop.setDeltaMovement(0, 0, 0);
                        drop.setNoPickUpDelay();
                        player.level.addFreshEntity(drop);
                    }
                }
            }
        }
    }
}
