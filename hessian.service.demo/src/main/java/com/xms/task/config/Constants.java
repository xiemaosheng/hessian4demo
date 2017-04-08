package com.xms.task.config;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // 第三方接入
    public static Map<String, String> APPS = new HashMap<String, String>();

    // 第三方接入时间戳超时时间
    public static final long TOKEN_EXPIRE = 2 * 60 * 1000;

    public static final Marker MARKER_INT = MarkerFactory.getMarker("INT");
    
    public static final Marker MARKER_EXT = MarkerFactory.getMarker("EXT");
    public static final String APP_CACHE = "APP_CACHE";
}

