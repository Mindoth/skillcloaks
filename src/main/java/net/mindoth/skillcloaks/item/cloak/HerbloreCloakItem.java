package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HerbloreCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.herblore_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
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

    @SubscribeEvent
    public static void onPlayerUseHerblore(final PlayerInteractEvent.RightClickItem event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        PlayerEntity player = event.getPlayer();
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.HERBLORE_CLOAK.get(), player).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) {

            ItemStack potionStack = BrewingRecipeRegistry.getOutput(event.getPlayer().getItemBySlot(EquipmentSlotType.MAINHAND), event.getPlayer().getItemBySlot(EquipmentSlotType.OFFHAND));
            ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), potionStack);

            ItemStack potionStack2 = BrewingRecipeRegistry.getOutput(event.getPlayer().getItemBySlot(EquipmentSlotType.OFFHAND), event.getPlayer().getItemBySlot(EquipmentSlotType.MAINHAND));
            ItemEntity drop2 = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), potionStack2);

            if ( potionStack.getItem() instanceof PotionItem || potionStack2.getItem() instanceof PotionItem ) {
                event.getPlayer().getItemBySlot(EquipmentSlotType.MAINHAND).shrink(1);
                event.getPlayer().getItemBySlot(EquipmentSlotType.OFFHAND).shrink(1);
                drop.setDeltaMovement(0, 0, 0);
                drop.setNoPickUpDelay();
                player.level.addFreshEntity(drop);
                drop2.setDeltaMovement(0, 0, 0);
                drop2.setNoPickUpDelay();
                player.level.addFreshEntity(drop2);

                //Sound
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BREWING_STAND_BREW, SoundCategory.PLAYERS, 1, 1);
            }
        }
    }
}
