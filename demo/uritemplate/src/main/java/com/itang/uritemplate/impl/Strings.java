package com.itang.uritemplate.impl;

import javax.annotation.Nullable;
import java.util.List;

final class Strings {
    private static final String PATH_SEPARATOR = "/";

    private Strings() {
        // ignore
    }

    public static String dropEndChar(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }


    /**
     * 转换为正确的URI.
     *
     * @param uri 带转换的uri
     */
    public static String asUri(String uri) {
        // 去掉头尾空字符
        String result = uri.trim();

        if (result.equals("")) {
            return PATH_SEPARATOR;
        }
        // 将 多个'/' 转换为单个 '/'
        result = result.replaceAll("/+", PATH_SEPARATOR);

        if (result.equals(PATH_SEPARATOR)) {
            return PATH_SEPARATOR;
        }

        // 去掉结尾'/'
        if (result.endsWith(PATH_SEPARATOR)) {
            result = Strings.dropEndChar(result);
        }

        // 以'/'打头
        if (!result.startsWith(PATH_SEPARATOR)) {
            result = PATH_SEPARATOR + result;
        }
        return result;
    }

    public static String asUriPattern(String pattern) {
        String result = asUri(pattern);

        return result;
    }
}
