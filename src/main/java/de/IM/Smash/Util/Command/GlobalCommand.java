package de.IM.Smash.Util.Command;



import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class GlobalCommand<S> {
    private JavaPlugin plugin;
    private CommandSubMap<S> commandMap;
    private Class playerClass;

    public GlobalCommand(JavaPlugin plugin, IMCommand command, Object function, Method method, Class playerClass) {
        this.plugin = plugin;
        this.commandMap = new CommandSubMap<>(new CommandProcessor<>(plugin, this, command, function, method));
        this.playerClass = playerClass;
    }

    public void addSubCommand(IMCommand command, Object function, Method method) {
        commandMap.putCommand(new CommandProcessor<>(plugin, this, command, function, method), Arrays.copyOfRange(command.parent(), 1, command.parent().length));
    }

    public void process(S sender, String[] args) {
        commandMap.run(sender, args);
    }

    public Class getPlayerClass() {
        return playerClass;
    }

    public abstract boolean checkPermission(S sender, String permission);
}

