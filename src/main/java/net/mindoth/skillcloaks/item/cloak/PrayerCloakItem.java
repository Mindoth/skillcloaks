package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class PrayerCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.prayer_cloak"));

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillCloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "cloak_armor").toString(), SkillCloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    private static final String TAG_HAS_PRAYER_CLOAK = ("saveXp");

    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return ICurio.DropRule.DEFAULT;
        else return ICurio.DropRule.ALWAYS_KEEP;
    }

    @SubscribeEvent
    public static void onPlayerXpDrop(final LivingExperienceDropEvent event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( event.getEntity() instanceof ServerPlayerEntity ) {
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntity();
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.PRAYER_CLOAK.get(), player).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLethal(final LivingDamageEvent event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        LivingEntity player = event.getEntityLiving();
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        if ( event.getAmount() >= player.getHealth() ) {
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.PRAYER_CLOAK.get(), player).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
                if ( !data.getBoolean(TAG_HAS_PRAYER_CLOAK) ) {
                    data.putBoolean(TAG_HAS_PRAYER_CLOAK, true);
                    playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(final PlayerEvent.Clone event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( event.isWasDeath() ) {
            CompoundNBT playerData = event.getOriginal().getPersistentData();
            CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            if (data.getBoolean(TAG_HAS_PRAYER_CLOAK)) {
                if ( !(event.getPlayer().totalExperience > 0) ) {
                    event.getPlayer().giveExperiencePoints(event.getOriginal().totalExperience);
                    data.remove(TAG_HAS_PRAYER_CLOAK);
                }
            }
        }
    }
}
