package net.blockhost.anarchymod;

import net.fabricmc.api.ClientModInitializer;

public class AnarchyMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Domains.initialize();
    }
}
