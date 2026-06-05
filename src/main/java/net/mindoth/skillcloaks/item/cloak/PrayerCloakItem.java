package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class PrayerCloakItem extends CurioItem {
    
    public PrayerCloakItem(String name) {
        super(name);
    }

    private static final String TAG_HAS_PRAYER_CLOAK = ("saveXp");

    @SuppressWarnings("ALL")
    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return ICurio.DropRule.DEFAULT;
        else return ICurio.DropRule.ALWAYS_KEEP;
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerXpDrop(final LivingExperienceDropEvent event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getEntity() instanceof ServerPlayer player ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.PRAYER_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
                event.setCanceled(true);
            }
        }
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerLethal(final LivingDamageEvent.Pre event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        LivingEntity player = event.getEntity();
        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
        if ( event.getOriginalDamage() >= player.getHealth() ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.PRAYER_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
                if ( !data.getBoolean(TAG_HAS_PRAYER_CLOAK) ) {
                    data.putBoolean(TAG_HAS_PRAYER_CLOAK, true);
                    playerData.put(Player.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(final PlayerEvent.Clone event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.isWasDeath() ) {
            CompoundTag playerData = event.getOriginal().getPersistentData();
            CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
            if ( data.getBoolean(TAG_HAS_PRAYER_CLOAK) ) {
                if ( !(event.getEntity().totalExperience > 0) ) {
                    event.getEntity().giveExperiencePoints(event.getOriginal().totalExperience);
                    data.remove(TAG_HAS_PRAYER_CLOAK);
                }
            }
        }
    }
}
