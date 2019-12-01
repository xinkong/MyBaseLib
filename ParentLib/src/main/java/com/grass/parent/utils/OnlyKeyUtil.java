package com.grass.parent.utils;

import java.util.UUID;

/**
 * 获取唯一key
 */
public class OnlyKeyUtil {

    public static String getEventKey() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
