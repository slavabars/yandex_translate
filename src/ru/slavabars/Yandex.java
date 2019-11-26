package ru.slavabars;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Yandex {
    private static final String YANDEX_API_KEY = "trnsl.1.1.20191125T102401Z.e0aee00d8c8bfba8.f07ae66c0388d5eb31d829bd48b78f8689f505dd";
    private static final String HOST = "translate.yandex.net";
    private static final String PATH = "/api/v1.5/tr.json/";

    public String translate(String text, String langFrom, String langTo) {
        final String method = "translate";

        String uri = "https://" + HOST + PATH + method;
        uri += "?key=" + YANDEX_API_KEY;
        uri += "&lang=" + langFrom + "-" + langTo;
        try {
            uri += "&text=" +  URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request(uri);
    }

    public String getLanguages(String ui) {
        final String method = "getLangs";
        String uri = "https://" + HOST + PATH + method;
        uri += "?key=" + YANDEX_API_KEY;
        uri += "&ui=" + ui;

        return request(uri);
    }

    @Nullable
    private String request(String uri) {
        try {
            URLConnection connection = new URL(uri).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();

            java.util.Scanner s = new java.util.Scanner(response, "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return null;
        }
    }

}
