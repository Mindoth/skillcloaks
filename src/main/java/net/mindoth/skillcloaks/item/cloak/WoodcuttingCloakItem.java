package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class WoodcuttingCloakItem extends CurioItem {

    public WoodcuttingCloakItem(String name) {
        super(name);
    }

    private static List<BlockPos> getLogsToBreak(Level world, BlockPos pos, List<BlockPos> logsToBreak) {
        List<BlockPos> checkAround = new ArrayList<BlockPos>();

        Iterator<BlockPos> aroundLogs = BlockPos.betweenClosedStream(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).iterator();
        while ( aroundLogs.hasNext() ) {
            BlockPos logPos = aroundLogs.next().immutable();
            if ( logsToBreak.contains((logPos)) ) continue;
            if ( world.getBlockState(logPos).is(BlockTags.LOGS) ) {
                checkAround.add(logPos);
                logsToBreak.add(logPos);
            }
        }
        if ( checkAround.size() == 0 ) return logsToBreak;
        for ( BlockPos capos : checkAround ) {
            for ( BlockPos logPos : getLogsToBreak(world, capos, logsToBreak) ) {
                if ( !logsToBreak.contains(logPos) ) {
                    logsToBreak.add(logPos.immutable());
                }
            }
        }
        BlockPos up = pos.above(2);
        return getLogsToBreak(world, up.immutable(), logsToBreak);
    }

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        Level world = event.getPlayer().level();
        if ( !world.isClientSide ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(event.getPlayer(), ModItems.WOODCUTTING_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(event.getPlayer(), ModItems.MAX_CLOAK.get()).isPresent() ) {
                if ( !event.getPlayer().isCrouching() ) {
                    if ( event.getState().is(BlockTags.LOGS) ) {
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
                            if ( isTree ) {
                                for (BlockPos logPos : logs) {
                                    world.getBlockState(logPos).getBlock().playerDestroy(world, event.getPlayer(), logPos, world.getBlockState(logPos), null, axeItem);
                                    world.removeBlock(logPos, false);
                                    if ( world instanceof ServerLevel serverLevel ) {
                                        Player player = event.getPlayer();
                                        axeItem.hurtAndBreak(1, serverLevel, player,
                                                (holder) -> player.onEquippedItemBroken(axeItem.getItem(), EquipmentSlot.OFFHAND));
                                    }
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
