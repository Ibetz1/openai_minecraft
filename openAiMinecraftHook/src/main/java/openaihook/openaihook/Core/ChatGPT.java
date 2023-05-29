package openaihook.openaihook.Core;

import com.google.gson.Gson;
import openaihook.openaihook.GeneralUtil;
import openaihook.openaihook.OpenAIHook;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatGPT {
    private static final String API_URL = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";

    // generate HTTP response from chatGPT
    @Nullable
    private static String fetchJsonResponse(String userInput, String context) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + API_KEY);

        // generate request data
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("model", "text-davinci-003");
        requestData.put("prompt", "the users message is '" + userInput + "' but " + context);
        requestData.put("max_tokens", 75);
        requestData.put("temperature", 0.87);
        requestData.put("top_p", 0.8);
        requestData.put("frequency_penalty", 1);
        requestData.put("presence_penalty", 1);

        // interpret request
        Gson gson = new Gson();
        String json = gson.toJson(requestData);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);

        try {

            // generate response
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {

            // cannot generate response
            e.printStackTrace();
            return "no response found :(";
        }
    }

    // interact with chatGPT
    public static void interact(Player p, String message) {
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String json = fetchJsonResponse(message, ContextGenerator.formatContext(p));

                    Gson gson = new Gson();
                    ChatGPTResponse response = gson.fromJson(json, ChatGPTResponse.class);
                    Choice[] choices = response.getChoices();

                    // remove weird formatting
                    String answer = choices[0].getText();
                    answer = answer.replace("AI - ", "");
                    answer = answer.replace("'", "\"");

                    // say the answer in chat
                    GeneralUtil.chatAI(answer);

                    if (answer.contains("\"/")) {
                        int lIndex = answer.indexOf("\"/");
                        String command = answer.substring(lIndex).split("\"")[1];
                        p.chat(command);
                    }

                    // add it to the log
                    ContextGenerator.addChatContext("user", message);
                    ContextGenerator.addChatContext("AI", answer);

                } catch (IOException e) {
                    p.sendMessage(ChatColor.RED + "ChatGPT could not be reached :(");
                }
            }
        };

        task.runTaskAsynchronously(OpenAIHook.getPlugin());
    }
}
