package com.example.url_shortener.utils;

public class UrlUtils {
    public static String generateShortId() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortId = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortId.append(characters.charAt(index));
        }
        return shortId.toString();
    }

    public static String normalizeUrl(String raw) {
        if (raw == null) {
            return "";
        }
        String url = raw.trim();

        //for the quotes issue i had in the test cases
        if (url.startsWith("\"") && url.endsWith("\"") && url.length() > 1) {
            url = url.substring(1, url.length() - 1);
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }

        return url;
    }

    public static boolean isValidUrl(String url) {
        try {
            new java.net.URI(url);
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }
}
