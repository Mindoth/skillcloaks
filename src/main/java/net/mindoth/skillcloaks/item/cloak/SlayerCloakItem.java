package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class SlayerCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(Component.translatable("tooltip.skillcloaks.slayer_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @SubscribeEvent
    public static void onSlayerHurt(final LivingDamageEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if (event.getEntity() instanceof Monster) {
            Level world = event.getEntity().level;
            if (!world.isClientSide) {
                if (event.getSource().getEntity() instanceof LivingEntity) {
                    LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
                    if ( attacker != null && ( CuriosApi.getCuriosHelper().findFirstCurio(attacker, SkillcloaksItems.SLAYER_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(attacker, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) ) {
                        LivingEntity target = event.getEntity();
                        if ( (target.getHealth() <= target.getMaxHealth() * SkillcloaksCommonConfig.SLAYER_THRESHOLD.get()) || (event.getAmount() >= target.getMaxHealth() * (1.0 - SkillcloaksCommonConfig.SLAYER_THRESHOLD.get())) || (event.getAmount() >= target.getHealth()) ) {
                            event.setAmount(Float.MAX_VALUE);
                            //Sound
                            world.playSound(null, target.getBoundingBox().getCenter().x, target.getBoundingBox().getCenter().y, target.getBoundingBox().getCenter().z,
                                    SoundEvents.BLAZE_HURT, SoundSource.PLAYERS, 1, 1);
                            //Particles
                            ServerLevel level = (ServerLevel)world;
                            for (int i = 0; i < 8; ++i) {
                                level.sendParticles(ParticleTypes.CRIT, target.getBoundingBox().getCenter().x, target.getBoundingBox().getCenter().y, target.getBoundingBox().getCenter().z, 10, 0, 0, 0, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
