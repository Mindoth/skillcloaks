package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.SackItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RedSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(Component.literal("Attack").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Defence").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Hitpoints").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Magic").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Prayer").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Ranging").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Strength").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            Random r = new Random();
            int number = r.nextInt(7);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.ATTACK_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.ATTACK_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.DEFENCE_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.DEFENCE_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.HITPOINTS_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.HITPOINTS_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 3) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.MAGIC_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.MAGIC_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 4) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.PRAYER_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.PRAYER_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 5) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.RANGING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.RANGING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 6) {
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.STRENGTH_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.STRENGTH_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            heldStack.shrink(1);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
        }
        return super.use(world, player, hand);
    }
}
