package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.SackItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BrownSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(new TranslationTextComponent("Cooking").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Crafting").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Fletching").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Herblore").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Runecraft").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Smithing").withStyle(TextFormatting.GRAY));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            Random r = new Random();
            int number = r.nextInt(6);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.COOKING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.COOKING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.CRAFTING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.CRAFTING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.FLETCHING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.FLETCHING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 3) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.HERBLORE_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.HERBLORE_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 4) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.RUNECRAFT_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.RUNECRAFT_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 5) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.SMITHING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.SMITHING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            else {
                System.out.println("NO ITEM FROM SACK FOR " + player.getName() + ". REPORT TO THE MOD AUTHOR");
            }

            heldStack.shrink(1);
            return new ActionResult<>(ActionResultType.SUCCESS, heldStack);
        }
        return super.use(world, player, hand);
    }
}
