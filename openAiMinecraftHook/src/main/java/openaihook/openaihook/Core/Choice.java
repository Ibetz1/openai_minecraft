package openaihook.openaihook.Core;

import com.google.gson.annotations.SerializedName;

class Choice {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }
}