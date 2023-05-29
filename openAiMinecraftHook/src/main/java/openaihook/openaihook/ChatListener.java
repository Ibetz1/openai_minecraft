package openaihook.openaihook;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import openaihook.openaihook.Core.ChatGPT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener implements Listener {

    private static ArrayList<String> getAiSummoners() {
        ArrayList<String> aiSummoners = new ArrayList<>();

        // summoners
        aiSummoners.add("ai");
        aiSummoners.add("gpt");
        aiSummoners.add("chatbot");
        aiSummoners.add("openAI");

        return aiSummoners;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        ArrayList<String> summoners = getAiSummoners();
        String message = event.getMessage();
        Player p = event.getPlayer();

        // check if player is talking to the ai
        for (String s : summoners) {
            if (message.toLowerCase().contains(s)) {
                ChatGPT.interact(event.getPlayer(), message);
                message = GeneralUtil.removeKeyWord(s, message);

                // globalize message
                TextComponent userName = GeneralUtil.generateName(p.getName(),
                        NamedTextColor.GREEN, GeneralUtil.GPTNameColor);

                OpenAIHook.getPlugin().getServer().sendMessage(
                        GeneralUtil.AIChatPrefix.append(userName)
                                .append(Component.text(" " + message, NamedTextColor.WHITE)));


                event.setCancelled(true);
                break;
            }
        }
    }

    public ChatListener(OpenAIHook plugin) { Bukkit.getPluginManager().registerEvents(this, plugin); }

}
