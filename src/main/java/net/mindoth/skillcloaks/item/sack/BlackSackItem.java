package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.registries.ModData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class BlackSackItem extends SackItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag tag = ModData.getOrCreateLegacyTag(stack);
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.black_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            if (!tag.contains("agility")) tooltip.add(Component.literal("Agility").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("attack")) tooltip.add(Component.literal("Attack").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("construction")) tooltip.add(Component.literal("Construction").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("cooking")) tooltip.add(Component.literal("Cooking").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("crafting")) tooltip.add(Component.literal("Crafting").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("defence")) tooltip.add(Component.literal("Defence").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("farming")) tooltip.add(Component.literal("Farming").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("firemaking")) tooltip.add(Component.literal("Firemaking").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("fishing")) tooltip.add(Component.literal("Fishing").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("fletching")) tooltip.add(Component.literal("Fletching").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("herblore")) tooltip.add(Component.literal("Herblore").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("hitpoints")) tooltip.add(Component.literal("Hitpoints").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("hunter")) tooltip.add(Component.literal("Hunter").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("magic")) tooltip.add(Component.literal("Magic").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("mining")) tooltip.add(Component.literal("Mining").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("prayer")) tooltip.add(Component.literal("Prayer").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("ranging")) tooltip.add(Component.literal("Ranging").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("runecraft")) tooltip.add(Component.literal("Runecraft").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("slayer")) tooltip.add(Component.literal("Slayer").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("smithing")) tooltip.add(Component.literal("Smithing").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("strength")) tooltip.add(Component.literal("Strength").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("thieving")) tooltip.add(Component.literal("Thieving").withStyle(ChatFormatting.GRAY));
            if (!tag.contains("woodcutting")) tooltip.add(Component.literal("Woodcutting").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    /*
    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            CompoundTag tag = ModData.getOrCreateLegacyTag(heldStack);
            for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                ItemStack cloakStack = player.getInventory().getItem(i);
                if (cloakStack.getItem() instanceof CurioItem) {
                    if ( cloakStack.getItem() == ModItems.AGILITY_CLOAK.get() ) {
                        TagSet(tag, "agility", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.ATTACK_CLOAK.get() ) {
                        TagSet(tag, "attack", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.CONSTRUCTION_CLOAK.get() ) {
                        TagSet(tag, "construction", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.COOKING_CLOAK.get() ) {
                        TagSet(tag, "cooking", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.CRAFTING_CLOAK.get() ) {
                        TagSet(tag, "crafting", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.DEFENCE_CLOAK.get() ) {
                        TagSet(tag, "defence", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.FARMING_CLOAK.get() ) {
                        TagSet(tag, "farming", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.FIREMAKING_CLOAK.get() ) {
                        TagSet(tag, "firemaking", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.FISHING_CLOAK.get() ) {
                        TagSet(tag, "fishing", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.FLETCHING_CLOAK.get() ) {
                        TagSet(tag, "fletching", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.HERBLORE_CLOAK.get() ) {
                        TagSet(tag, "herblore", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.HITPOINTS_CLOAK.get() ) {
                        TagSet(tag, "hitpoints", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.HUNTER_CLOAK.get() ) {
                        TagSet(tag, "hunter", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.MAGIC_CLOAK.get() ) {
                        TagSet(tag, "magic", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.MINING_CLOAK.get() ) {
                        TagSet(tag, "mining", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.PRAYER_CLOAK.get() ) {
                        TagSet(tag, "prayer", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.RANGING_CLOAK.get() ) {
                        TagSet(tag, "ranging", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.RUNECRAFT_CLOAK.get() ) {
                        TagSet(tag, "runecraft", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.SLAYER_CLOAK.get() ) {
                        TagSet(tag, "slayer", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.SMITHING_CLOAK.get() ) {
                        TagSet(tag, "smithing", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.STRENGTH_CLOAK.get() ) {
                        TagSet(tag, "strength", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.THIEVING_CLOAK.get() ) {
                        TagSet(tag, "thieving", heldStack, cloakStack);
                    }
                    if ( cloakStack.getItem() == ModItems.WOODCUTTING_CLOAK.get() ) {
                        TagSet(tag, "woodcutting", heldStack, cloakStack);
                    }
                }
            }
            if ( tag.contains("agility") && tag.contains("attack") && tag.contains("construction") && tag.contains("cooking") && tag.contains("crafting")
                    && tag.contains("defence") && tag.contains("farming") && tag.contains("firemaking") && tag.contains("fishing") && tag.contains("fletching")
                    && tag.contains("herblore") && tag.contains("hitpoints") && tag.contains("hunter") && tag.contains("magic") && tag.contains("mining")
                    && tag.contains("prayer") && tag.contains("ranging") && tag.contains("runecraft") && tag.contains("slayer") && tag.contains("smithing")
                    && tag.contains("strength") && tag.contains("thieving") && tag.contains("woodcutting") ) {
                heldStack.shrink(1);
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MAX_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MAX_HOOD.get()));
                this.giveItem(player, cloak, hood);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
            }
        }
        return super.use(world, player, hand);
    }

    private void TagSet(CompoundTag tag, String string, ItemStack heldStack, ItemStack cloakStack) {
        if (tag.contains(string)) return;
        tag.putBoolean(string, true);
        ModData.setLegacyTag(heldStack, tag);
        cloakStack.shrink(1);
    }
    */
}
