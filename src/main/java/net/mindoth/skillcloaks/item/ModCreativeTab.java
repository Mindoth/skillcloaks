package net.mindoth.skillcloaks.item;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Skillcloaks.MOD_ID);

    public static final Supplier<CreativeModeTab> SKILLCLOAKS_TAB = CREATIVE_MODE_TABS.register("skillcloaks_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.STICK)).title(Component.translatable("itemGroup.skillcloaks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        for ( DeferredHolder<Item, ? extends Item> item : ModItems.ITEMS.getEntries() ) output.accept(item.get());
                    }).build());
}
