package openaihook.openaihook.Core;

import com.google.gson.annotations.SerializedName;

class ChatGPTResponse {
    @SerializedName("choices")
    private Choice[] choices;

    public Choice[] getChoices() {
        return choices;
    }
}