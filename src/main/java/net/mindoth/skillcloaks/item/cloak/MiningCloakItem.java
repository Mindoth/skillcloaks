package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;

public class MiningCloakItem extends CurioItem {

    public MiningCloakItem(String name) {
        super(name);
    }

    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return 0;
        else return ModCommonConfig.MINING_LUCK.get();
    }
}
