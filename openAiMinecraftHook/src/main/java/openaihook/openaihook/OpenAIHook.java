package openaihook.openaihook;

import openaihook.openaihook.Core.ContextGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpenAIHook extends JavaPlugin {

    private static OpenAIHook plugin;

    public static OpenAIHook getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        try {
            new ConfigData("config");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        new ChatListener(this);
        ContextGenerator.generateServerContext();

        GeneralUtil.logConsole("Started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        GeneralUtil.logConsole("Stopped");
    }
}
