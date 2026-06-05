package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class SmithingCloakItem extends CurioItem {

    public SmithingCloakItem(String name) {
        super(name);
    }

    private static BlastingRecipe getBlastingRecipe(Player player, SingleRecipeInput inv) {
        Level level = player.level();
        Optional<RecipeHolder<BlastingRecipe>> optional = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, inv, level);
        return optional.map(RecipeHolder::value).orElse(null);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerUseSmithingBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (ModCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            SingleRecipeInput slotInv = new SingleRecipeInput(mainHandItemStack);
            SingleRecipeInput slotInvOff = new SingleRecipeInput(offHandItemStack);
            BlastingRecipe recipe = getBlastingRecipe(player, slotInv);
            BlastingRecipe recipeOff = getBlastingRecipe(player, slotInvOff);

            if ((recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
            }
        }
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerUseSmithing(final PlayerInteractEvent.RightClickItem event) {
        if (ModCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        if ( !(player.level() instanceof ServerLevel world) ) return;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            SingleRecipeInput slotInv = new SingleRecipeInput(mainHandItemStack);
            SingleRecipeInput slotInvOff = new SingleRecipeInput(offHandItemStack);
            BlastingRecipe recipe = getBlastingRecipe(player, slotInv);
            BlastingRecipe recipeOff = getBlastingRecipe(player, slotInvOff);

            if ( (recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
                //Check if Flint and Steel in offhand
                if (offHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = mainHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (offHandItemStack.getItem() instanceof FlintAndSteelItem && offHandItemStack.getDamageValue() <= offHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipe.assemble(slotInv, world.registryAccess());
                            if (!result.isEmpty()) {
                                mainHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level().addFreshEntity(drop);

                                offHandItemStack.hurtAndBreak(1, world, player,
                                        (holder) -> player.onEquippedItemBroken(offHandItemStack.getItem(), EquipmentSlot.OFFHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z,
                            SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
                }
                else if (mainHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = offHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (mainHandItemStack.getItem() instanceof FlintAndSteelItem && mainHandItemStack.getDamageValue() <= mainHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipeOff.assemble(slotInv, world.registryAccess());
                            if (!result.isEmpty()) {
                                offHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level().addFreshEntity(drop);

                                mainHandItemStack.hurtAndBreak(1, world, player,
                                        (holder) -> player.onEquippedItemBroken(mainHandItemStack.getItem(), EquipmentSlot.MAINHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z,
                            SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
                }
            }
        }
    }
}
