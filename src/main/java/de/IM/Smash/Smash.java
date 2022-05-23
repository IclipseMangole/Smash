package de.IM.Smash;

import org.bukkit.plugin.java.JavaPlugin;

public class Smash extends JavaPlugin {

    private static Smash smash;
    private static Data data;

    public static Smash getSmash() {
        return smash;
    }

    public static Data getData() {
        return data;
    }

    @Override
    public void onLoad() {
        smash = this;
        data = new Data();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListener() {

    }

    private void registerCommands() {

    }
}
