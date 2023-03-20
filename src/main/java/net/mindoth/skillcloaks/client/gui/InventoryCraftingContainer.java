package net.mindoth.skillcloaks.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class InventoryCraftingContainer extends WorkbenchContainer {

    public InventoryCraftingContainer(int p_i50089_1_, PlayerInventory p_i50089_2_) {
        super(p_i50089_1_, p_i50089_2_);
    }

    public InventoryCraftingContainer(int p_i50090_1_, PlayerInventory p_i50090_2_, IWorldPosCallable p_i50090_3_) {
        super(p_i50090_1_, p_i50090_2_, p_i50090_3_);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }
}
