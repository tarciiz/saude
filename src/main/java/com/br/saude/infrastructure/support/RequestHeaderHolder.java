package com.br.saude.infrastructure.support;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderHolder {
    private static final Map<String, String> THREAD_LOCAL = new HashMap<>();

    public static Map<String, String> getHeaders() {
        return THREAD_LOCAL;
    }

    public static void addToHeaders(String key, String val) {
        THREAD_LOCAL.put(key, val);
    }

    public static String getFromHeaders(String key) {
        return THREAD_LOCAL.get(key);
    }

    public static void clearHeaders() {
        THREAD_LOCAL.clear();
    }
}
