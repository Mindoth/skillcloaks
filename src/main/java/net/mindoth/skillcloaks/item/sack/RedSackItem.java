package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.SackItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
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

public class RedSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.loot_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            tooltip.add(new TranslationTextComponent("Attack").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Defence").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Hitpoints").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Magic").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Prayer").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Ranging").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("Strength").withStyle(TextFormatting.GRAY));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            Random r = new Random();
            int number = r.nextInt(7);
            if (number == 0) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.ATTACK_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.ATTACK_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 1) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.DEFENCE_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.DEFENCE_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 2) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.HITPOINTS_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.HITPOINTS_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 3) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.MAGIC_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.MAGIC_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 4) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.PRAYER_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.PRAYER_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 5) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.RANGING_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.RANGING_HOOD.get()));
                this.giveItem(player, cloak, hood);
            }
            if (number == 6) {
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.STRENGTH_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillCloaksItems.STRENGTH_HOOD.get()));
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
