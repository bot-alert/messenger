package com.ashishkhatiwada.chat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCacheUtil {

    public static final Set<String> userSet = new HashSet<>();

    public static void add(String userId) {
        userSet.add(userId);
    }

    public static boolean containsUserId(String userId) {
        return userSet.contains(userId);
    }


    public static void removeUserId(String userId) {
        userSet.remove(userId);
    }
}
