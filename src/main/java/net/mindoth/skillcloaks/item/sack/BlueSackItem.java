package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.SackItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
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

public class BlueSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(Component.literal("Agility").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Slayer").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Thieving").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            Random r = new Random();
            int number = r.nextInt(3);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.AGILITY_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.AGILITY_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.SLAYER_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.SLAYER_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.THIEVING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.THIEVING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            else {
                System.out.println("NO ITEM FROM SACK FOR " + player.getName() + ". REPORT TO THE MOD AUTHOR");
            }
            heldStack.shrink(1);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
        }
        return super.use(world, player, hand);
    }
}
