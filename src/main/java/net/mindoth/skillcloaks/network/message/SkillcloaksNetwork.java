package net.mindoth.skillcloaks.network.message;

import net.mindoth.skillcloaks.Skillcloaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SkillcloaksNetwork {
    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Skillcloaks.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void init() {
        CHANNEL.registerMessage(0, CloakAbilityPacket.class, CloakAbilityPacket::encode, CloakAbilityPacket::decode, CloakAbilityPacket::handle);
    }
}
