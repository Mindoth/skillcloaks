package net.mindoth.skillcloaks.item.sack;

import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModData;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

public class BlackSackItem extends SackItem {

    private final String AGILITY = "agility";
    private final String ATTACK = "attack";
    private final String CONSTRUCTION = "construction";
    private final String COOKING = "cooking";
    private final String CRAFTING = "crafting";
    private final String DEFENCE = "defence";
    private final String FARMING = "farming";
    private final String FIREMAKING = "firemaking";
    private final String FISHING = "fishing";
    private final String FLETCHING = "fletching";
    private final String HERBLORE = "herblore";
    private final String HITPOINTS = "hitpoints";
    private final String HUNTER = "hunter";
    private final String MAGIC = "magic";
    private final String MINING = "mining";
    private final String PRAYER = "prayer";
    private final String RANGING = "ranging";
    private final String RUNECRAFT = "runecraft";
    private final String SLAYER = "slayer";
    private final String SMITHING = "smithing";
    private final String STRENGTH = "strength";
    private final String THIEVING = "thieving";
    private final String WOODCUTTING = "woodcutting";

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag tag = ModData.getOrCreateLegacyTag(stack);
        if ( !Screen.hasShiftDown() ) {
            tooltip.add(Component.translatable("tooltip.skillcloaks.black_sack"));
        }
        if ( Screen.hasShiftDown() ) {
            if (!tag.contains(AGILITY)) tooltip.add(Component.literal("Agility").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(ATTACK)) tooltip.add(Component.literal("Attack").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(CONSTRUCTION)) tooltip.add(Component.literal("Construction").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(COOKING)) tooltip.add(Component.literal("Cooking").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(CRAFTING)) tooltip.add(Component.literal("Crafting").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(DEFENCE)) tooltip.add(Component.literal("Defence").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(FARMING)) tooltip.add(Component.literal("Farming").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(FIREMAKING)) tooltip.add(Component.literal("Firemaking").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(FISHING)) tooltip.add(Component.literal("Fishing").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(FLETCHING)) tooltip.add(Component.literal("Fletching").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(HERBLORE)) tooltip.add(Component.literal("Herblore").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(HITPOINTS)) tooltip.add(Component.literal("Hitpoints").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(HUNTER)) tooltip.add(Component.literal("Hunter").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(MAGIC)) tooltip.add(Component.literal("Magic").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(MINING)) tooltip.add(Component.literal("Mining").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(PRAYER)) tooltip.add(Component.literal("Prayer").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(RANGING)) tooltip.add(Component.literal("Ranging").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(RUNECRAFT)) tooltip.add(Component.literal("Runecraft").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(SLAYER)) tooltip.add(Component.literal("Slayer").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(SMITHING)) tooltip.add(Component.literal("Smithing").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(STRENGTH)) tooltip.add(Component.literal("Strength").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(THIEVING)) tooltip.add(Component.literal("Thieving").withStyle(ChatFormatting.GRAY));
            if (!tag.contains(WOODCUTTING)) tooltip.add(Component.literal("Woodcutting").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if ( !world.isClientSide ) {
            CompoundTag tag = ModData.getOrCreateLegacyTag(heldStack);
            if ( tag.contains(AGILITY) && tag.contains(ATTACK) && tag.contains(CONSTRUCTION) && tag.contains(COOKING) && tag.contains(CRAFTING)
                    && tag.contains(DEFENCE) && tag.contains(FARMING) && tag.contains(FIREMAKING) && tag.contains(FISHING) && tag.contains(FLETCHING)
                    && tag.contains(HERBLORE) && tag.contains(HITPOINTS) && tag.contains(HUNTER) && tag.contains(MAGIC) && tag.contains(MINING)
                    && tag.contains(PRAYER) && tag.contains(RANGING) && tag.contains(RUNECRAFT) && tag.contains(SLAYER) && tag.contains(SMITHING)
                    && tag.contains(STRENGTH) && tag.contains(THIEVING) && tag.contains(WOODCUTTING) ) {
                heldStack.shrink(1);
                ItemEntity cloak = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MAX_CLOAK.get()));
                ItemEntity hood = new ItemEntity(player.level(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(ModItems.MAX_HOOD.get()));
                this.giveItem(player, cloak, hood);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, heldStack);
            }
            else {
                for ( int i = 0; i < player.getInventory().getContainerSize(); ++i ) {
                    ItemStack cloakStack = player.getInventory().getItem(i);
                    if (cloakStack.getItem() instanceof CurioItem) {
                        if ( cloakStack.getItem() == ModItems.AGILITY_CLOAK.get() ) {
                            TagSet(tag, AGILITY, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.ATTACK_CLOAK.get() ) {
                            TagSet(tag, ATTACK, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.CONSTRUCTION_CLOAK.get() ) {
                            TagSet(tag, CONSTRUCTION, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.COOKING_CLOAK.get() ) {
                            TagSet(tag, COOKING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.CRAFTING_CLOAK.get() ) {
                            TagSet(tag, CRAFTING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.DEFENCE_CLOAK.get() ) {
                            TagSet(tag, DEFENCE, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.FARMING_CLOAK.get() ) {
                            TagSet(tag, FARMING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.FIREMAKING_CLOAK.get() ) {
                            TagSet(tag, FIREMAKING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.FISHING_CLOAK.get() ) {
                            TagSet(tag, FISHING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.FLETCHING_CLOAK.get() ) {
                            TagSet(tag, FLETCHING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.HERBLORE_CLOAK.get() ) {
                            TagSet(tag, HERBLORE, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.HITPOINTS_CLOAK.get() ) {
                            TagSet(tag, HITPOINTS, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.HUNTER_CLOAK.get() ) {
                            TagSet(tag, HUNTER, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.MAGIC_CLOAK.get() ) {
                            TagSet(tag, MAGIC, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.MINING_CLOAK.get() ) {
                            TagSet(tag, MINING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.PRAYER_CLOAK.get() ) {
                            TagSet(tag, PRAYER, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.RANGING_CLOAK.get() ) {
                            TagSet(tag, RANGING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.RUNECRAFT_CLOAK.get() ) {
                            TagSet(tag, RUNECRAFT, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.SLAYER_CLOAK.get() ) {
                            TagSet(tag, SLAYER, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.SMITHING_CLOAK.get() ) {
                            TagSet(tag, SMITHING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.STRENGTH_CLOAK.get() ) {
                            TagSet(tag, STRENGTH, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.THIEVING_CLOAK.get() ) {
                            TagSet(tag, THIEVING, heldStack, cloakStack);
                        }
                        if ( cloakStack.getItem() == ModItems.WOODCUTTING_CLOAK.get() ) {
                            TagSet(tag, WOODCUTTING, heldStack, cloakStack);
                        }
                    }
                }
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
}
