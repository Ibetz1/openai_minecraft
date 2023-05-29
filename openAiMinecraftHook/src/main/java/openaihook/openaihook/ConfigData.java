package openaihook.openaihook;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigData {
    public static int chatContextSize = 10;



    ///////////////////////////
    // config interpretation //
    ///////////////////////////

    private FileConfiguration config;

    public FileConfiguration getConfig() {
        return this.config;
    }

    // instance and generate config data
    public ConfigData(String name) throws IllegalAccessException {
        try {
            this.generateConfig(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateConfig(String name) throws IllegalAccessException, IOException {
        File configFile = new File(OpenAIHook.getPlugin().getDataFolder(), name + ".yml");

        // generate file
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            OpenAIHook.getPlugin().saveResource(name + ".yml", false);
        }

        // load config
        config = YamlConfiguration.loadConfiguration(configFile);

        Field[] fields = this.getClass().getDeclaredFields();

        // load fields into config
        for (Field f : fields) {
            int modifiers = f.getModifiers();

            // verify non-private
            if (!Modifier.isPrivate(modifiers)) {

                // generate key
                if (!config.contains(f.getName())) {
                    GeneralUtil.logConsole("generating config key: " + f.getName() + ", " + f.get(this).toString());
                    config.set(f.getName(), f.get(this));

                    // internalize key
                } else {
                    String path = f.getName();

                    if (config.isBoolean(path)) f.set(this, config.getBoolean(path));
                    if (config.isInt(path)) f.set(this, config.getInt(path));
                    if (config.isDouble(path)) f.set(this, config.getDouble(path));
                    if (config.isString(path)) f.set(this, config.getString(path));
                    if (config.isLong(path)) f.set(this, config.getLong(path));

                    GeneralUtil.logConsole("loaded config key: " + f.getName() + ", " + f.get(this));
                }
            }
        }

        config.save(configFile);
    }
}
