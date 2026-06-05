package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class DefenceCloakItem extends CurioItem {

    public DefenceCloakItem(String name) {
        super(name);
    }

    public static final String TAG_DEFENCE_COOLDOWN = ("preventDeath");

    //Prevent death
    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onDamageEvent(final LivingDamageEvent.Pre event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( !event.getEntity().level().isClientSide ) {
            LivingEntity player = event.getEntity();
            CompoundTag playerData = player.getPersistentData();
            CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);

            //Check if the damage is lethal and that the player has the correct cloak equipped
            if ( ( event.getOriginalDamage() >= event.getEntity().getHealth() )
                    && ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.DEFENCE_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) ) {

                //Check for cooldown
                if ( data.getInt(TAG_DEFENCE_COOLDOWN) <= 0 ) {
                    event.setNewDamage(0);
                    player.setHealth(1.0F);
                    player.removeAllEffects();
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1, 1);

                    //Add cooldown
                    data.putInt(TAG_DEFENCE_COOLDOWN, Math.abs(ModCommonConfig.DEFENCE_COOLDOWN.get()));
                    playerData.put(Player.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    //Defence cloak timer
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
        if ( !player.level().isClientSide ) {

            if ( player.tickCount % 2 == 0 ) {
                //Check for cooldown
                if (data.getInt(TAG_DEFENCE_COOLDOWN) > 0) {

                    //Lower cooldown by 1 each tick
                    data.putInt(TAG_DEFENCE_COOLDOWN, data.getInt(TAG_DEFENCE_COOLDOWN) - 1);
                    playerData.put(Player.PERSISTED_NBT_TAG, data);
                }
            }

            //Inform the player that the cloak has recharged when 1 is left in cooldown so chat doesn't get spammed
            if ( data.getInt(TAG_DEFENCE_COOLDOWN) == 1 ) {
                player.displayClientMessage(Component.translatable("message.skillcloaks.defence.recharged"), true);
                player.playNotifySound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.PLAYERS, 1, 1);
            }

            //Remove the cooldown tag
            if ( data.getInt(TAG_DEFENCE_COOLDOWN) <= 0 ) {
                data.remove(TAG_DEFENCE_COOLDOWN);
            }
        }
    }

    //Warn the player when they equip the cloak while on cooldown
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        timeEquip(slotContext, prevStack, stack);
    }

    public static void timeEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if ( livingEntity instanceof Player ) {
            Player player = (Player)livingEntity;
            CompoundTag playerData = player.getPersistentData();
            CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
            if ( !player.level().isClientSide && ( data.getInt(TAG_DEFENCE_COOLDOWN) > 0 ) ) {
                int totalSecs = data.getInt(TAG_DEFENCE_COOLDOWN) / 20;
                int days = totalSecs / 86400;
                int hours = (totalSecs % 86400) / 3600;
                int mins = (totalSecs % 3600) / 60;
                int secs = totalSecs % 60;
                if ( days > 0 ) {
                    player.displayClientMessage(Component.translatable("message.skillcloaks.defence.cooldown")
                            .append(Component.literal(days + "d " + hours + "h " + mins + "m " + secs + "s")), true);
                }
                else if ( hours > 0 ) {
                    player.displayClientMessage(Component.translatable("message.skillcloaks.defence.cooldown")
                            .append(Component.literal(hours + "h " + mins + "m " + secs + "s")), true);
                }
                else if ( mins > 0 ) {
                    player.displayClientMessage(Component.translatable("message.skillcloaks.defence.cooldown")
                            .append(Component.literal(mins + "m " + secs + "s")), true);
                }
                else player.displayClientMessage(Component.translatable("message.skillcloaks.defence.cooldown")
                            .append(Component.literal(secs + "s")), true);
                player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE.value(), SoundSource.PLAYERS, 1, 0.5f);
            }
        }
    }
}
