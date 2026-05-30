package net.mindoth.skillcloaks.item.sack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class BrownSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(Component.literal("Cooking").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Crafting").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Fletching").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Herblore").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Runecraft").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Smithing").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    /*
    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            Random r = new Random();
            int number = r.nextInt(6);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.COOKING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.COOKING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.CRAFTING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.CRAFTING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FLETCHING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FLETCHING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 3) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.HERBLORE_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.HERBLORE_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 4) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.RUNECRAFT_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.RUNECRAFT_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 5) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.SMITHING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.SMITHING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            heldStack.shrink(1);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
        }
        return super.use(world, player, hand);
    }
     */
}
