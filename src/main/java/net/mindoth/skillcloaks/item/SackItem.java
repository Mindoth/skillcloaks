package net.mindoth.skillcloaks.item;

import net.mindoth.skillcloaks.itemgroup.SkillCloaksItemGroup;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;

public class SackItem extends Item {

    public SackItem(Properties properties) {
        super(properties.tab(SkillCloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant());
    }

    public SackItem() {
        this(new Properties());
    }

    public void giveItem(Player player, @Nonnull ItemEntity cloak, @Nonnull ItemEntity hood) {
        cloak.setDeltaMovement(0, 0, 0);
        cloak.setNoPickUpDelay();
        player.level.addFreshEntity(cloak);
        hood.setDeltaMovement(0, 0, 0);
        hood.setNoPickUpDelay();
        player.level.addFreshEntity(hood);
    }
}
