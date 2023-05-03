package net.mindoth.skillcloaks.item;

import net.mindoth.skillcloaks.itemgroup.SkillcloaksItemGroup;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class SackItem extends Item {

    public SackItem(Properties properties) {
        super(properties.tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant());
    }

    public SackItem() {
        this(new Properties());
    }

    public void giveItem(PlayerEntity player, @Nonnull ItemEntity cloak, @Nonnull ItemEntity hood) {
        cloak.setDeltaMovement(0, 0, 0);
        cloak.setNoPickUpDelay();
        player.level.addFreshEntity(cloak);
        hood.setDeltaMovement(0, 0, 0);
        hood.setNoPickUpDelay();
        player.level.addFreshEntity(hood);
    }
}
