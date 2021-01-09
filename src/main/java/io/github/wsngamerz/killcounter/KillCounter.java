package io.github.wsngamerz.killcounter;

import org.bukkit.plugin.java.JavaPlugin;

public class KillCounter extends JavaPlugin {

    @Override
    public void onEnable() {
        DataManager.getManager().setup(this);
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("kills").setExecutor(new KillsCommand());
        getCommand("kills").setTabCompleter(new KillCounterTabComplete());
    }

    @Override
    public void onDisable() {}
}
