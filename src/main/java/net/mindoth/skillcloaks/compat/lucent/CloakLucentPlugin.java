package net.mindoth.skillcloaks.compat.lucent;


import com.legacy.lucent.api.EntityBrightness;
import com.legacy.lucent.api.plugin.ILucentPlugin;
import com.legacy.lucent.api.plugin.LucentPlugin;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

@LucentPlugin
public class CloakLucentPlugin implements ILucentPlugin {

    @Override
    public String ownerModID() {
        return Skillcloaks.MOD_ID;
    }

    @Override
    public void getEntityLightLevel(EntityBrightness entityBrightness) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Entity entity = entityBrightness.getEntity();

        if (entity instanceof Player) {
            Player player = (Player)entity;
            if ( ModList.get().isLoaded("curios") && ModList.get().isLoaded("lucent") && SkillcloaksCommonConfig.LUCENT_COMPAT.get() ) {
                if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.FIREMAKING_CLOAK.get()).isPresent() ) {
                    entityBrightness.setLightLevel(15);
                }
                if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {
                    entityBrightness.setLightLevel(15);
                }
            }
        }
    }
}
