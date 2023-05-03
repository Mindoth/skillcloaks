package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HunterCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslatableComponent("tooltip.skillcloaks.hunter_cloak"));

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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHunterCloakDrops(final LivingDropsEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( event.getSource().getEntity() instanceof LivingEntity ) {
            LivingEntity player = (LivingEntity)event.getSource().getEntity();
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.HUNTER_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {
                Collection<ItemEntity> drops = event.getDrops();
                for (ItemEntity drop : drops) {
                    drop.moveTo(player.getX(), player.getY(), player.getZ());
                    drop.setDeltaMovement(0, 0, 0);
                    drop.setNoPickUpDelay();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHunterCloakXpDrop(final LivingExperienceDropEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( event.getAttackingPlayer() instanceof Player) {
            Player player = event.getAttackingPlayer();
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.HUNTER_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {
                Level world = player.level;
                int experience = event.getDroppedExperience();
                ExperienceOrb experienceOrbEntity = new ExperienceOrb(world, player.getX(), player.getY(), player.getZ(), experience);
                experienceOrbEntity.setDeltaMovement(0, 0, 0);
                world.addFreshEntity(experienceOrbEntity);
                event.setCanceled(true);
            }
        }
    }
}
