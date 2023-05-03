package net.mindoth.skillcloaks.network.message;

import net.mindoth.skillcloaks.client.gui.InventoryCraftingContainer;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkEvent;
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

    public static void encode(CloakAbilityPacket message, PacketBuffer buffer) {
        buffer.writeInt(message.key);
    }

    public static CloakAbilityPacket decode(PacketBuffer buffer) {
        return new CloakAbilityPacket(buffer.readInt());
    }

    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.crafting");

    public static INamedContainerProvider getMenuProvider(World p_220052_2_, BlockPos p_220052_3_) {
        return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
            return new InventoryCraftingContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.create(p_220052_2_, p_220052_3_));
        }, CONTAINER_TITLE);
    }

    public static void handle(CloakAbilityPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            //Player
            ServerPlayerEntity player = context.getSender();
            //World
            World world = player.getLevel();
            //Data of your cloak
            CompoundNBT playerMaxData = player.getPersistentData();
            CompoundNBT maxData = playerMaxData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);



            //Max Cloak
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent()
                    && !player.getCooldowns().isOnCooldown(SkillcloaksItems.MAX_CLOAK.get())
                    && player.isCrouching() ) {

                switch ( maxData.getInt(TAG_MAX_MODE) ) {
                    case 1:
                        maxData.putInt(TAG_MAX_MODE, 2);
                        playerMaxData.put(PlayerEntity.PERSISTED_NBT_TAG, maxData);
                        player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.max.mode.construction"), true);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                        break;
                    case 2:
                        if (ModList.get().isLoaded("lucent")) {
                            maxData.putInt(TAG_MAX_MODE, 1);
                        }
                        else maxData.putInt(TAG_MAX_MODE, 3);
                        playerMaxData.put(PlayerEntity.PERSISTED_NBT_TAG, maxData);
                        player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.max.mode.crafting"), true);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                        break;
                    case 3:
                        maxData.putInt(TAG_MAX_MODE, 1);
                        playerMaxData.put(PlayerEntity.PERSISTED_NBT_TAG, maxData);
                        player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.max.mode.firemaking"), true);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                        break;
                    default:
                        maxData.putInt(TAG_MAX_MODE, 2);
                        playerMaxData.put(PlayerEntity.PERSISTED_NBT_TAG, maxData);
                        player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.max.mode.construction"), true);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                        break;
                }
            }



            //Crafting Cloak, open a crafting menu
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.CRAFTING_CLOAK.get(), player).isPresent()
                    || (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() && !player.isCrouching()) ) {
                if (!ModList.get().isLoaded("lucent")) {
                    if (maxData.getInt(TAG_MAX_MODE) == 3 || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.CRAFTING_CLOAK.get(), player).isPresent()) {
                        player.openMenu(getMenuProvider(player.level, player.blockPosition()));
                    }
                }
                else if (maxData.getInt(TAG_MAX_MODE) == 1 || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.CRAFTING_CLOAK.get(), player).isPresent()) {
                    player.openMenu(getMenuProvider(player.level, player.blockPosition()));
                }
            }



            //Construction cloak, TP player to their bed and give them the cooldown
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.CONSTRUCTION_CLOAK.get(), player).isPresent()
                    || (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent()
                    && maxData.getInt(TAG_MAX_MODE) == 2
                    && !player.isCrouching()) ) {

                if (!player.getCooldowns().isOnCooldown(SkillcloaksItems.CONSTRUCTION_CLOAK.get()) && !player.getCooldowns().isOnCooldown(SkillcloaksItems.MAX_CLOAK.get())) {

                    //Getting player's bed location
                    ServerWorld respawnWorld = player.server.getLevel(player.getRespawnDimension());
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
                                    world.playSound(null, spawn.getX() + 0.5, spawn.getY() + 1.5D, spawn.getZ() + 0.5, SoundEvents.BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 0.5f);

                                    //Particles
                                    for (int i = 0; i < 360; i++) {
                                        if (i % 20 == 0) {
                                            respawnWorld.sendParticles(ParticleTypes.FLAME, spawn.getX() + 0.5 + Math.cos(i) * 1.5D, spawn.getY() + 1.5D, spawn.getZ() + 0.5 + Math.sin(i) * 1.5D, 1, 0, 0, 0, 0);
                                        }
                                    }
                                } else {
                                    //Sound
                                    world.playSound(null, spawn.getX() + 0.5, spawn.getY() + 1.5D, spawn.getZ() + 0.5, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1, 1);
                                    //Particles
                                    for (int i = 0; i < 10; ++i) {
                                        respawnWorld.sendParticles(ParticleTypes.PORTAL, spawn.getX() + 0.5, spawn.getY() + 1, spawn.getZ() + 0.5, (int) (player.getRandom().nextDouble() * 10), (player.getRandom().nextDouble() - 0.5D) * 1.5D, -player.getRandom().nextDouble() + 1, (player.getRandom().nextDouble() - 0.5D) * 1.5D, 0);
                                    }
                                }
                            } else {
                                player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.construction.cantfind"), true);
                                player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1, 0.5f);
                            }
                        } else {
                            player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.construction.wrongdim"), true);
                            player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1, 0.5f);
                        }
                        //Add cooldown to player
                        player.getCooldowns().addCooldown(SkillcloaksItems.CONSTRUCTION_CLOAK.get(), 20);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                    }
                    else {
                        player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.construction.cantfind"), true);
                        player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1, 0.5f);
                    }
                }
            }



            if (!ModList.get().isLoaded("lucent")) {
                //Firemaking Cloak, give the player a torch and put the ability on cooldown
                if (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.FIREMAKING_CLOAK.get(), player).isPresent()
                        || (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent()
                        && maxData.getInt(TAG_MAX_MODE) == 1
                        && !player.isCrouching()) ) {

                    //Either give the player a torch or drop it on them
                    if (!player.getCooldowns().isOnCooldown(SkillcloaksItems.FIREMAKING_CLOAK.get()) && !player.getCooldowns().isOnCooldown(SkillcloaksItems.MAX_CLOAK.get())) {

                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1, 1);

                        //Drop a torch on the player
                        ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(Items.TORCH));
                        drop.setDeltaMovement(0, 0, 0);
                        drop.setNoPickUpDelay();
                        player.level.addFreshEntity(drop);

                        //Add cooldown to player
                        player.getCooldowns().addCooldown(SkillcloaksItems.FIREMAKING_CLOAK.get(), 20);
                        player.getCooldowns().addCooldown(SkillcloaksItems.MAX_CLOAK.get(), 20);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }

}
