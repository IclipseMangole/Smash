package de.IM.Smash;

import de.IM.Smash.Util.WorldCopy;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
        //loadLobby();
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
    /*
    public void loadLobby() {
        File from = new File("/home/IMNetzwerk/Welten/Bauserver/Lobbys/IMLobby");
        File to = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile().getAbsolutePath() + "/world");

        WorldCopy.copy(from, to, false);
    }

     */
}
