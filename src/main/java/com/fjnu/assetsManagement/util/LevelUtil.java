package com.fjnu.assetsManagement.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by willim on 2018/10/15.
 */
public class LevelUtil {
    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4
    public static String calculateLevel(String parentLevel, Long parentId) {
        return __calculateLevel(parentLevel, parentId.toString());
    }

    public static String calculateLevel(String parentLevel, String parentId) {
        return __calculateLevel(parentLevel, parentId);
    }

    private static String __calculateLevel(String parentLevel, String parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
