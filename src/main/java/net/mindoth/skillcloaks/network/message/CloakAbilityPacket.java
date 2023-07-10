package net.mindoth.skillcloaks.network.message;

import net.mindoth.skillcloaks.client.InventoryCraftingMenu;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class CloakAbilityPacket {

    public static final UUID UNION_UUID = UUID.fromString("e8ef4b7d-f91a-4a40-b020-c44386355641");
    private static final String TAG_MAX_MODE = ("skillcloak.mode");

    public int key;

    public CloakAbilityPacket() {
    }

    public CloakAbilityPacket(int key) {
        this.key = key;
    }

    public static void encode(CloakAbilityPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.key);
    }

    public static CloakAbilityPacket decode(FriendlyByteBuf buffer) {
        return new CloakAbilityPacket(buffer.readInt());
    }

    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public static MenuProvider getMenuProvider(Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> {
            return new InventoryCraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.create(pLevel, pPos));
        }, CONTAINER_TITLE);
    }

    public static void handle(CloakAbilityPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            //Player
            ServerPlayer player = context.getSender();
            //World
            Level world = player.getLevel();
            //Data of your cloak
            CompoundTag playerMaxData = player.getPersistentData();
            CompoundTag maxData = playerMaxData.getCompound(Player.PERSISTED_NBT_TAG);



            //Max Cloak
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent()
                    && !player.getCooldowns().isOnCooldown(SkillcloaksItems.MAX_CLOAK.get())
                    && player.isCrouching() ) {

                if (maxData.getInt(TAG_MAX_MODE) == 2) {
                    maxData.putInt(TAG_MAX_MODE, 1);
                    playerMaxData.put(Player.PERSISTED_NBT_TAG, maxData);
                    player.displayClientMessage(Component.translatable("message.skillcloaks.max.mode.construction"), true);
                    player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                }
                else {
                    maxData.putInt(TAG_MAX_MODE, 2);
                    playerMaxData.put(Player.PERSISTED_NBT_TAG, maxData);
                    player.displayClientMessage(Component.translatable("message.skillcloaks.max.mode.crafting"), true);
                    player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                }
            }



            //Crafting Cloak, open a crafting menu
            if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.CRAFTING_CLOAK.get()).isPresent()
                    || (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent()
                    && maxData.getInt(TAG_MAX_MODE) == 2
                    && !player.isCrouching())) {
                player.openMenu(getMenuProvider(player.level, player.blockPosition()));
            }



            //Construction cloak, TP player to their bed and give them the cooldown
            if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.CONSTRUCTION_CLOAK.get()).isPresent()
                    || ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent()
                    && maxData.getInt(TAG_MAX_MODE) == 1
                    && !player.isCrouching())) {

                if (!player.getCooldowns().isOnCooldown(SkillcloaksItems.CONSTRUCTION_CLOAK.get()) && !player.getCooldowns().isOnCooldown(SkillcloaksItems.MAX_CLOAK.get())) {

                    //Getting player's bed location
                    ServerLevel respawnWorld = player.server.getLevel(player.getRespawnDimension());
                    BlockPos spawn = player.getRespawnPosition();

                    if (spawn != null && respawnWorld != null) {
                        BlockState blockstate = respawnWorld.getBlockState(spawn);

                        //Check if player's bed exists in the same dimension
                        if (respawnWorld == world) {
                            //Check if player has set a spawnpoint
                            if (blockstate.is(Blocks.RESPAWN_ANCHOR) && blockstate.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(respawnWorld) || blockstate.is(BlockTags.BEDS)) {

                                //TP player
                                player.moveTo(spawn.getX() + 0.5, spawn.getY() + 1, spawn.getZ() + 0.5);
                                //Reduce anchor usage
                                if (blockstate.is(Blocks.RESPAWN_ANCHOR)) {
                                    respawnWorld.setBlock(spawn, blockstate.setValue(RespawnAnchorBlock.CHARGE, blockstate.getValue(RespawnAnchorBlock.CHARGE) - 1), 3);
                                }

                                if (Objects.equals(player.getUUID(), UNION_UUID)) {
                                    //Sound
                                    world.playSound(null, spawn.getX() + 0.5, spawn.getY() + 1.5D, spawn.getZ() + 0.5, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1, 0.5f);
                                    //Particles
                                    for (int i = 0; i < 360; i++) {
                                        if (i % 20 == 0) {
                                            respawnWorld.sendParticles(ParticleTypes.FLAME, spawn.getX() + 0.5 + Math.cos(i) * 1.5D, spawn.getY() + 1.5D, spawn.getZ() + 0.5 + Math.sin(i) * 1.5D, 1, 0, 0, 0, 0);
                                        }
                                    }
                                } else {
                                    //Sound
                                    world.playSound(null, spawn.getX() + 0.5, spawn.getY() + 1.5D, spawn.getZ() + 0.5, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1, 1);
                                    //Particles
                                    for (int i = 0; i < 10; ++i) {
                                        respawnWorld.sendParticles(ParticleTypes.PORTAL, spawn.getX() + 0.5, spawn.getY() + 1, spawn.getZ() + 0.5, (int) (player.getRandom().nextDouble() * 10), (player.getRandom().nextDouble() - 0.5D) * 1.5D, -player.getRandom().nextDouble() + 1, (player.getRandom().nextDouble() - 0.5D) * 1.5D, 0);
                                    }
                                }
                            } else {
                                player.displayClientMessage(Component.translatable("message.skillcloaks.construction.cantfind"), true);
                                player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundSource.PLAYERS, 1, 0.5f);
                            }
                        } else {
                            player.displayClientMessage(Component.translatable("message.skillcloaks.construction.wrongdim"), true);
                            player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundSource.PLAYERS, 1, 0.5f);
                        }
                        //Add cooldown to player
                        player.getCooldowns().addCooldown(SkillcloaksItems.CONSTRUCTION_CLOAK.get(), 20);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                    }
                    else {
                        player.displayClientMessage(Component.translatable("message.skillcloaks.construction.cantfind"), true);
                        player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundSource.PLAYERS, 1, 0.5f);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }

}
