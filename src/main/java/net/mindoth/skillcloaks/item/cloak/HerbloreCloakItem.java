package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HerbloreCloakItem extends CurioItem {

    public HerbloreCloakItem(String name) {
        super(name);
    }

    @SubscribeEvent
    public static void onPlayerUse(final PlayerInteractEvent.RightClickItem event) {
        if (ModCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        Level level = player.level();
        if ( level.isClientSide() ) return;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HERBLORE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack potionStack0 = getOutput(level, player.getItemBySlot(EquipmentSlot.MAINHAND), player.getItemBySlot(EquipmentSlot.OFFHAND));
            ItemStack potionStack1 = getOutput(level, player.getItemBySlot(EquipmentSlot.OFFHAND), player.getItemBySlot(EquipmentSlot.MAINHAND));

            if ( potionStack0.getItem() instanceof PotionItem && isNotSame(potionStack0, player) ) makeMix(potionStack0, player, level);
            else if ( potionStack1.getItem() instanceof PotionItem && isNotSame(potionStack1, player) ) makeMix(potionStack1, player, level);
        }
    }

    private static ItemStack getOutput(Level level, ItemStack input, ItemStack ingredient) {
        return level.potionBrewing().mix(input, ingredient);
    }

    private static boolean isNotSame(ItemStack potionStack, Player player) {
        return potionStack.get(DataComponents.POTION_CONTENTS) != player.getItemBySlot(EquipmentSlot.MAINHAND).get(DataComponents.POTION_CONTENTS)
                && potionStack.get(DataComponents.POTION_CONTENTS) != player.getItemBySlot(EquipmentSlot.OFFHAND).get(DataComponents.POTION_CONTENTS);
    }

    private static void makeMix(ItemStack potionStack, Player player, Level level) {
        ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, potionStack);
        player.getItemBySlot(EquipmentSlot.MAINHAND).shrink(1);
        player.getItemBySlot(EquipmentSlot.OFFHAND).shrink(1);
        drop.setDeltaMovement(0, 0, 0);
        drop.setNoPickUpDelay();
        level.addFreshEntity(drop);

        //Sound
        level.playSound(null, player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z,
                SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 1, 1);
    }
}
