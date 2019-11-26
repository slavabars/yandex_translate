package ru.slavabars;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class YandexResponse {
    int code;
    String lang;
    List<String> text;

    @SerializedName("dirs")
    List<String> pairs;

    @SerializedName("langs")
    Map<String, String> langs;

    public int getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }

    public List<String> getPairs() {
        return pairs;
    }

    public Map<String, String> getLangs() {
        return langs;
    }
}
