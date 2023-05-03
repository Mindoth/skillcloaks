package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class WoodcuttingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get())
            tooltip.add(new TranslatableComponent("tooltip.skillcloaks.woodcutting_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslatableComponent("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(new TranslatableComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    private static List<BlockPos> getLogsToBreak(Level world, BlockPos pos, List<BlockPos> logsToBreak) {
        List<BlockPos> checkAround = new ArrayList<BlockPos>();

        Iterator<BlockPos> aroundLogs = BlockPos.betweenClosedStream(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).iterator();
        while (aroundLogs.hasNext()) {
            BlockPos logPos = aroundLogs.next().immutable();
            if (logsToBreak.contains((logPos))) {
                continue;
            }
            if (world.getBlockState(logPos).is(BlockTags.LOGS)) {
                checkAround.add(logPos);
                logsToBreak.add(logPos);
            }
        }
        if (checkAround.size() == 0) {
            return logsToBreak;
        }
        for (BlockPos capos : checkAround) {
            for (BlockPos logPos : getLogsToBreak(world, capos, logsToBreak)) {
                if (!logsToBreak.contains(logPos)) {
                    logsToBreak.add(logPos.immutable());
                }
            }
        }
        BlockPos up = pos.above(2);
        return getLogsToBreak(world, up.immutable(), logsToBreak);
    }

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Level world = event.getPlayer().level;
        if (!world.isClientSide) {
            if (CuriosApi.getCuriosHelper().findFirstCurio(event.getPlayer(), SkillcloaksItems.WOODCUTTING_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(event.getPlayer(), SkillcloaksItems.MAX_CLOAK.get()).isPresent()) {
                if (!event.getPlayer().isCrouching()) {
                    if (event.getState().is(BlockTags.LOGS)) {
                        List<BlockPos> logs = WoodcuttingCloakItem.getLogsToBreak(world, event.getPos(), new ArrayList<BlockPos>());
                        ItemStack axeItem = event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND);
                        int durability = axeItem.getMaxDamage() - axeItem.getDamageValue();
                        if (axeItem.getItem() instanceof AxeItem && durability >= logs.size()) {
                            boolean isTree = false;
                            for (BlockPos leafPos : logs) {
                                Stream<BlockPos> aroundLogs = BlockPos.betweenClosedStream(leafPos.getX() - 1, leafPos.getY() - 1, leafPos.getZ() - 1, leafPos.getX() + 1, leafPos.getY() + 1, leafPos.getZ() + 1);
                                if (aroundLogs.anyMatch(t -> world.getBlockState(new BlockPos.MutableBlockPos(t.getX(), t.getY(), t.getZ())).is(BlockTags.LEAVES)
                                        && world.getBlockState(new BlockPos.MutableBlockPos(t.getX(), t.getY(), t.getZ())).hasProperty(PERSISTENT)
                                        && !world.getBlockState(new BlockPos.MutableBlockPos(t.getX(), t.getY(), t.getZ())).getValue(PERSISTENT))) {
                                    isTree = true;
                                }
                            }
                            if (isTree) {
                                for (BlockPos logPos : logs) {
                                    world.getBlockState(logPos).getBlock().playerDestroy(world, event.getPlayer(), logPos, world.getBlockState(logPos), null, axeItem);
                                    world.removeBlock(logPos, false);
                                    axeItem.hurtAndBreak(1, event.getPlayer(), (holder) -> holder.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                                }
                            }
                        }
                        else {
                            world.playSound(null, event.getPlayer().getX(), event.getPlayer().getY() + 1, event.getPlayer().getZ(),
                                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1, 0.5f);
                        }
                    }
                }
            }
        }
    }
}