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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class SlayerCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.slayer_cloak"));

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

    @SubscribeEvent
    public static void onSlayerHurt(final LivingDamageEvent event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( event.getEntityLiving() instanceof MonsterEntity ) {
            World world = event.getEntityLiving().level;
            if ( !world.isClientSide ) {
                if ( event.getSource().getEntity() instanceof LivingEntity ) {
                    LivingEntity attacker = (LivingEntity)event.getSource().getEntity();
                    if ( attacker != null && ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SLAYER_CLOAK.get(), attacker).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), attacker).isPresent() ) ) {
                        LivingEntity target = event.getEntityLiving();
                        if ( (target.getHealth() <= target.getMaxHealth() * SkillCloaksCommonConfig.SLAYER_THRESHOLD.get()) || (event.getAmount() >= target.getMaxHealth() * (1.0 - SkillCloaksCommonConfig.SLAYER_THRESHOLD.get())) || (event.getAmount() >= target.getHealth()) ) {
                            event.setAmount(Float.MAX_VALUE);
                            //Sound
                            world.playSound(null, target.getX(), target.getY(), target.getZ(),
                                    SoundEvents.BLAZE_HURT, SoundCategory.PLAYERS, 1, 1);
                            //Particles
                            ServerWorld level = (ServerWorld)world;
                            for (int i = 0; i < 8; ++i) {
                                level.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1, target.getZ(), 10, 0, 0, 0, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
