package openaihook.openaihook;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import java.util.regex.Pattern;

import javax.naming.Name;

public class GeneralUtil {
    public static TextColor GPTNameColor = TextColor.color(0, 169, 189);

    public static TextComponent AIChatPrefix = Component.text("AI", NamedTextColor.GRAY)
        .append(Component.text(" | ", NamedTextColor.AQUA).decorate(TextDecoration.BOLD));


    public static void logConsole(String message) {
        ConsoleCommandSender console = OpenAIHook.getPlugin().getServer().getConsoleSender();
        String tag = ChatColor.LIGHT_PURPLE + "[Open-AI] ";
        console.sendMessage(tag + ChatColor.WHITE + message);
    }

    public static TextComponent generateName(String name, TextColor borderColor, TextColor nameColor) {
        return Component.text("<", borderColor)
        .append(Component.text(name, nameColor))
        .append(Component.text(">", borderColor));
    }

    public static void chatAI(String message) {
        message = message.replace("  ", " ");
        message = message.replace("\n", " ");

        TextComponent chat = AIChatPrefix.append(generateName("GPT-3", NamedTextColor.RED, GPTNameColor))
                             .append(Component.text(message, NamedTextColor.WHITE));

        OpenAIHook.getPlugin().getServer().sendMessage(chat);
    }

    public static String removeKeyWord(String word, String sentence) {
        String newSentence = sentence.replaceAll("(?i)" + Pattern.quote(word), "");
        newSentence = newSentence.replaceAll("^\\s+", "");
        newSentence = newSentence.replaceAll("\\s+$", "");
        newSentence = newSentence.replaceAll("\\s+", " ");
        return newSentence;
    }
}
