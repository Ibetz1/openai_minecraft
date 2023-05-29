package openaihook.openaihook.Core;
import openaihook.openaihook.ConfigData;
import openaihook.openaihook.OpenAIHook;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;

public class ContextGenerator {
    private static final ArrayList<String> chatContext = new ArrayList<>();
    private static String serverContext = "";

    public static void generateServerContext() {
        File file = new File(OpenAIHook.getPlugin().getDataFolder(), "serverInfo.txt");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // specify the file name to be read
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            StringBuilder context = new StringBuilder();
            while ((line = br.readLine()) != null) {
                context.append(line).append("\n");
            }
            serverContext = context.toString();

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public static String generatePlayerContext(Player p) {
        String username = p.getName();
        String UUID = p.getUniqueId().toString();
        String ping = p.getPing() + "";

        return "the users name is " + username + "the users uuid is " + UUID + " the users ping is " + ping;
    };

    public static void addChatContext(String sender, String message) {
        if (ConfigData.chatContextSize <= 0) return;
        if (chatContext.size() > ConfigData.chatContextSize) {
            chatContext.remove(0);
        }

        chatContext.add(sender + " - '" + message + "'\n");
    }

    public static String getChatContext() {
        StringBuilder context = new StringBuilder();
        for (String s : chatContext) {
            context.append(s);
        }
        return context + "\n";
    }

    public static String formatContext(Player p) {
        String preface = "respond as if you are talking to the sender of this message with this context in mind '";
        String context = "your recent conversation was: ";
        context += getChatContext();
        context += "\nsome stuff about the user is: " + generatePlayerContext(p);
        context += "\nsome info about the server: " + serverContext;
        context += "'";

        context += "\ndo not recite any of this contexts formatting in your responses.";
        context += "\nif the user asks you how to execute a command just recite the command in chat.";
        context += "\nbe aware that you are on a minecraft server, if you do not know certain information say so.";
        context += "\nkeep your answer to a maximum of 1 sentence.";
        context += "\nif the user asks you to run a command, respond with just the command and no other text.";
        context += "\nalso remember you are able to execute commands for other people.";
        return preface + context;
    }
}
