package net.mindoth.skillcloaks.item.sack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class GreenSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(Component.literal("Farming").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Fishing").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Hunter").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Mining").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Woodcutting").withStyle(ChatFormatting.GRAY));
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
            int number = r.nextInt(5);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FARMING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FARMING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FISHING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.FISHING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.HUNTER_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.HUNTER_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 3) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MINING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MINING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 4) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.WOODCUTTING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.WOODCUTTING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            heldStack.shrink(1);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
        }
        return super.use(world, player, hand);
    }
     */
}
