package io.github.wsngamerz.killcounter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

// TODO: 02/08/2018 Add MySQL Support
// TODO: 02/08/2018 Add Sqlite Support

// TODO: 09/08/2018 Move all data related stuff into dataManager class

public class DataManager {
    private DataManager() {};
    private static DataManager manager = new DataManager();

    private FileConfiguration config;
    private String dataFormat;

    private File dataFolder;

    public static DataManager getManager() {
        return manager;
    }

    public void setup(Plugin plugin) {

        File pluginFolder = plugin.getDataFolder();
        dataFolder = new File(pluginFolder, "data");

        if (!(pluginFolder.exists())) {
            System.out.println(pluginFolder.mkdir());
        }

        if (!(dataFolder.exists())) {
            System.out.println(dataFolder.mkdir());
        }

        plugin.saveDefaultConfig();

        config = plugin.getConfig();
        dataFormat = config.getString("options.data-storage");

    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getUserString(String player, String category, String key) {
        switch (dataFormat) {
            case "yaml":
                File userFile = new File(dataFolder, player + ".yml");
                YamlConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
                String dataPath = category + "." + key;
                return userData.getString(dataPath, "");

            case "mysql":
                return "";

            case "sqlite":
                return "";

            default:
                return null;
        }
    }

    public void setUserString(String player, String category, String key, String value) {
        switch (dataFormat) {
            case "yaml":
                File userFile = new File(dataFolder, player + ".yml");
                YamlConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
                String dataPath = category + "." + key;
                userData.set(dataPath, value);

                try {
                    userData.save(userFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case "mysql":
                // MySQL

            case "sqlite":
                // SQLite

            default:
                // N/A
        }
    }

    public Integer getUserInt(String player, String category, String key) {
        switch (dataFormat) {
            case "yaml":
                File userFile = new File(dataFolder, player + ".yml");
                YamlConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
                String dataPath = category + "." + key;
                return userData.getInt(dataPath, 0);

            case "mysql":
                return 0;

            case "sqlite":
                return 0;

            default:
                return null;
        }
    }

    public void setUserInt(String player, String category, String key, Integer value) {
        switch (dataFormat) {
            case "yaml":
                File userFile = new File(dataFolder, player + ".yml");
                YamlConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
                String dataPath = category + "." + key;
                userData.set(dataPath, value);

                try {
                    userData.save(userFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case "mysql":
                // MySQL

            case "sqlite":
                // SQLite

            default:
                // N/A
        }
    }

}
