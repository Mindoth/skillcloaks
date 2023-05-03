package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.item.SackItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

public class BlackSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT tag = stack.getOrCreateTag();
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.black_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            if (!tag.contains("agility")) tooltip.add(new TranslationTextComponent("Agility").withStyle(TextFormatting.GRAY));
            if (!tag.contains("attack")) tooltip.add(new TranslationTextComponent("Attack").withStyle(TextFormatting.GRAY));
            if (!tag.contains("construction")) tooltip.add(new TranslationTextComponent("Construction").withStyle(TextFormatting.GRAY));
            if (!tag.contains("cooking")) tooltip.add(new TranslationTextComponent("Cooking").withStyle(TextFormatting.GRAY));
            if (!tag.contains("crafting")) tooltip.add(new TranslationTextComponent("Crafting").withStyle(TextFormatting.GRAY));
            if (!tag.contains("defence")) tooltip.add(new TranslationTextComponent("Defence").withStyle(TextFormatting.GRAY));
            if (!tag.contains("farming")) tooltip.add(new TranslationTextComponent("Farming").withStyle(TextFormatting.GRAY));
            if (!tag.contains("firemaking")) tooltip.add(new TranslationTextComponent("Firemaking").withStyle(TextFormatting.GRAY));
            if (!tag.contains("fishing")) tooltip.add(new TranslationTextComponent("Fishing").withStyle(TextFormatting.GRAY));
            if (!tag.contains("fletching")) tooltip.add(new TranslationTextComponent("Fletching").withStyle(TextFormatting.GRAY));
            if (!tag.contains("herblore")) tooltip.add(new TranslationTextComponent("Herblore").withStyle(TextFormatting.GRAY));
            if (!tag.contains("hitpoints")) tooltip.add(new TranslationTextComponent("Hitpoints").withStyle(TextFormatting.GRAY));
            if (!tag.contains("hunter")) tooltip.add(new TranslationTextComponent("Hunter").withStyle(TextFormatting.GRAY));
            if (!tag.contains("magic")) tooltip.add(new TranslationTextComponent("Magic").withStyle(TextFormatting.GRAY));
            if (!tag.contains("mining")) tooltip.add(new TranslationTextComponent("Mining").withStyle(TextFormatting.GRAY));
            if (!tag.contains("prayer")) tooltip.add(new TranslationTextComponent("Prayer").withStyle(TextFormatting.GRAY));
            if (!tag.contains("ranging")) tooltip.add(new TranslationTextComponent("Ranging").withStyle(TextFormatting.GRAY));
            if (!tag.contains("runecraft")) tooltip.add(new TranslationTextComponent("Runecraft").withStyle(TextFormatting.GRAY));
            if (!tag.contains("slayer")) tooltip.add(new TranslationTextComponent("Slayer").withStyle(TextFormatting.GRAY));
            if (!tag.contains("smithing")) tooltip.add(new TranslationTextComponent("Smithing").withStyle(TextFormatting.GRAY));
            if (!tag.contains("strength")) tooltip.add(new TranslationTextComponent("Strength").withStyle(TextFormatting.GRAY));
            if (!tag.contains("thieving")) tooltip.add(new TranslationTextComponent("Thieving").withStyle(TextFormatting.GRAY));
            if (!tag.contains("woodcutting")) tooltip.add(new TranslationTextComponent("Woodcutting").withStyle(TextFormatting.GRAY));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            CompoundNBT tag = heldStack.getOrCreateTag();
            for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
                ItemStack cloakStack = player.inventory.getItem(i);
                if (cloakStack.getItem() instanceof CurioItem) {
                    if ( !tag.contains("agility") && cloakStack.getItem() == SkillcloaksItems.AGILITY_CLOAK.get() ) {
                        tag.putBoolean("agility", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("attack") && cloakStack.getItem() == SkillcloaksItems.ATTACK_CLOAK.get() ) {
                        tag.putBoolean("attack", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("construction") && cloakStack.getItem() == SkillcloaksItems.CONSTRUCTION_CLOAK.get() ) {
                        tag.putBoolean("construction", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("cooking") && cloakStack.getItem() == SkillcloaksItems.COOKING_CLOAK.get() ) {
                        tag.putBoolean("cooking", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("crafting") && cloakStack.getItem() == SkillcloaksItems.CRAFTING_CLOAK.get() ) {
                        tag.putBoolean("crafting", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("defence") && cloakStack.getItem() == SkillcloaksItems.DEFENCE_CLOAK.get() ) {
                        tag.putBoolean("defence", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("farming") && cloakStack.getItem() == SkillcloaksItems.FARMING_CLOAK.get() ) {
                        tag.putBoolean("farming", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("firemaking") && cloakStack.getItem() == SkillcloaksItems.FIREMAKING_CLOAK.get() ) {
                        tag.putBoolean("firemaking", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("fishing") && cloakStack.getItem() == SkillcloaksItems.FISHING_CLOAK.get() ) {
                        tag.putBoolean("fishing", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("fletching") && cloakStack.getItem() == SkillcloaksItems.FLETCHING_CLOAK.get() ) {
                        tag.putBoolean("fletching", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("herblore") && cloakStack.getItem() == SkillcloaksItems.HERBLORE_CLOAK.get() ) {
                        tag.putBoolean("herblore", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("hitpoints") && cloakStack.getItem() == SkillcloaksItems.HITPOINTS_CLOAK.get() ) {
                        tag.putBoolean("hitpoints", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("hunter") && cloakStack.getItem() == SkillcloaksItems.HUNTER_CLOAK.get() ) {
                        tag.putBoolean("hunter", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("magic") && cloakStack.getItem() == SkillcloaksItems.MAGIC_CLOAK.get() ) {
                        tag.putBoolean("magic", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("mining") && cloakStack.getItem() == SkillcloaksItems.MINING_CLOAK.get() ) {
                        tag.putBoolean("mining", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("prayer") && cloakStack.getItem() == SkillcloaksItems.PRAYER_CLOAK.get() ) {
                        tag.putBoolean("prayer", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("ranging") && cloakStack.getItem() == SkillcloaksItems.RANGING_CLOAK.get() ) {
                        tag.putBoolean("ranging", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("runecraft") && cloakStack.getItem() == SkillcloaksItems.RUNECRAFT_CLOAK.get() ) {
                        tag.putBoolean("runecraft", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("slayer") && cloakStack.getItem() == SkillcloaksItems.SLAYER_CLOAK.get() ) {
                        tag.putBoolean("slayer", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("smithing") && cloakStack.getItem() == SkillcloaksItems.SMITHING_CLOAK.get() ) {
                        tag.putBoolean("smithing", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("strength") && cloakStack.getItem() == SkillcloaksItems.STRENGTH_CLOAK.get() ) {
                        tag.putBoolean("strength", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("thieving") && cloakStack.getItem() == SkillcloaksItems.THIEVING_CLOAK.get() ) {
                        tag.putBoolean("thieving", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                    if ( !tag.contains("woodcutting") && cloakStack.getItem() == SkillcloaksItems.WOODCUTTING_CLOAK.get() ) {
                        tag.putBoolean("woodcutting", true);
                        heldStack.setTag(tag);
                        cloakStack.shrink(1);
                    }
                }
            }
            if ( tag.contains("agility") && tag.contains("attack") && tag.contains("construction") && tag.contains("cooking") && tag.contains("crafting")
                    && tag.contains("defence") && tag.contains("farming") && tag.contains("firemaking") && tag.contains("fishing") && tag.contains("fletching")
                    && tag.contains("herblore") && tag.contains("hitpoints") && tag.contains("hunter") && tag.contains("magic") && tag.contains("mining")
                    && tag.contains("prayer") && tag.contains("ranging") && tag.contains("runecraft") && tag.contains("slayer") && tag.contains("smithing")
                    && tag.contains("strength") && tag.contains("thieving") && tag.contains("woodcutting") ) {
                heldStack.shrink(1);
                ItemEntity cloak = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.MAX_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(SkillcloaksItems.MAX_HOOD.get()));
                this.giveItem(player, cloak, hood);
                return new ActionResult<>(ActionResultType.SUCCESS, heldStack);
            }
        }
        return super.use(world, player, hand);
    }
}
