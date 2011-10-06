package com.itang.uritemplate.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itang.uritemplate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URI 模板默认实现.
 */
public class UriTemplateImpl implements UriTemplate {
    private final String _pattern;
    private final PathVariableResolver _pathVariableResolver;

    /**
     * 构造方法.
     *
     * @param pattern uri匹配模式
     */
    public UriTemplateImpl(final String pattern) {
        this(pattern, new PathVariableResolverImpl());
    }

    /**
     * 构造方法.
     *
     * @param pattern              uri匹配模式
     * @param pathVariableResolver 路径变量解析器
     */
    public UriTemplateImpl(final String pattern, final PathVariableResolver pathVariableResolver) {
        Preconditions.checkNotNull(pattern, "pattern 不能为null");
        Preconditions.checkNotNull(pattern, "pathVariableResolver 不能为null");

        this._pattern = Strings.asUriPattern(pattern);
        this._pathVariableResolver = pathVariableResolver;
    }

    public String getPattern() {
        return this._pattern;
    }

    public List<String> getVariableNames() {
        return getPathVariableResolver().getVariableNames(getPattern());
    }

    public boolean matches(String uri) {
        Preconditions.checkNotNull(uri, "uri 不能为null");
        return getPathVariableResolver().matches(getPattern(), Strings.asUri(uri)).isMatched();
    }

    public PathMatcher matcher(String uri) {
        Preconditions.checkNotNull(uri, "uri 不能为null");
        return getPathVariableResolver().matcher(getPattern(), Strings.asUri(uri));
    }

    public String toURI(Map<String, Object> variables) {
        throw new UnsupportedOperationException("未实现!");
    }

    public PathVariableResolver getPathVariableResolver() {
        return _pathVariableResolver;
    }

    /**
     * 路径解析器接口.
     */
    public static interface PathVariableResolver {
        public List<String> getVariableNames(String pattern);

        public RegexMatcher matches(String pattern, String uri);

        public PathMatcher matcher(String pattern, String uri);
    }

    private static class PathVariableResolverImpl implements PathVariableResolver {
        private static Logger logger = LoggerFactory.getLogger(PathVariableResolverImpl.class);

        // 路径变量定义开始标示字符
        private static final char START = '{';
        // 路径变量定义结束标示字符
        private static final char END = '}';

        public List<String> getVariableNames(final String pattern) {
            boolean isUnderParsing = false;// 处于匹配中
            final List<String> result = Lists.newArrayList();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0, len = pattern.length(); i < len; i++) {
                char c = pattern.charAt(i);
                if (c == START) {
                    isUnderParsing = true;
                    //sb.delete(0, sb.length());// clear
                    sb.setLength(0); //clear
                    continue;
                } else if (c == END) {
                    if (isUnderParsing) {
                        result.add(sb.toString());// add one
                        isUnderParsing = false;
                    }
                    continue;
                } else if (isUnderParsing) {
                    sb.append(c);
                }
            }
            return result;
        }


        public RegexMatcher matches(final String pattern, final String uri) {
            // 直接相等, 立即返回true (有大小写之分)
            if (pattern.equals(uri)) {
                return RegexMatcher.matched();
            }

            // 将pattern转换为regex pattern,进行正则表达式匹配
            final String patternAsRegex = patternToRegex(pattern, this.getVariableNames(pattern));
            final Matcher matcher = Pattern.compile(patternAsRegex).matcher(uri);
            if (matcher.matches()) {
                return new RegexMatcher(true, matcher);
            }

            return RegexMatcher.unMatched();
        }

        public PathMatcher matcher(final String pattern, final String uri) {
            final RegexMatcher regexMatcher = matches(pattern, uri);
            if (regexMatcher.isMatched()) {
                Map<String, String> variables = regexMatcher.getMetcher() == null ?
                        Maps.<String, String>newHashMap()
                        : getMatchedVariables(pattern, regexMatcher);
                return PathMatchers.matched(variables);

            }
            return PathMatchers.NON_MATCHER;
        }

        private Map<String, String> getMatchedVariables(final String pattern, final RegexMatcher regexMatcher) {
            final Map<String, String> result = Maps.newLinkedHashMap();
            if (regexMatcher.isMatched()) {   //匹配后，进行变量解析
                List<String> variableNames = this.getVariableNames(pattern);
                Matcher matcher = regexMatcher.getMetcher();
                for (int i = 1, len = matcher.groupCount(); i <= len; i++) {
                    result.put(variableNames.get(i - 1), matcher.group(i));
                }
            }
            return result;
        }

        /**
         * 将pattern转换为regex pattern.
         */
        private String patternToRegex(String pattern, List<String> variableNames) {
            String patternAsRegex = pattern;
            // /{some} -> /?{some}
            // '.' -> '\.'
            patternAsRegex = patternAsRegex.replaceAll("/\\{", "/?{");
            patternAsRegex = patternAsRegex.replaceAll("\\.", "\\\\.");
            for (String var : variableNames) {
                //变量定义转换为Regex
                //变量字符不包含'/','.' TODO 更多字符排除
                patternAsRegex = patternAsRegex.replaceAll("\\{" + var + "\\}",
                        "([^/.]+)?");
            }

            if (logger.isTraceEnabled()) {
                logger.trace(String.format("pattern '%s' with variables [%s] to regex:%s",
                        pattern, Arrays.toString(variableNames.toArray()), patternAsRegex));
            }

            return patternAsRegex;
        }
    }

    private static class RegexMatcher {
        private final boolean matched;
        private final Matcher matcher;
        private static RegexMatcher MATCHED_INSTANCE = new RegexMatcher(true, null);
        private static RegexMatcher UNMATCHED_INSTANCE = new RegexMatcher(false, null);

        public static RegexMatcher matched() {
            return MATCHED_INSTANCE;
        }

        public static RegexMatcher unMatched() {
            return UNMATCHED_INSTANCE;
        }

        public RegexMatcher(boolean matched, Matcher matcher) {
            this.matched = matched;
            this.matcher = matcher;
        }

        public boolean isMatched() {
            return matched;
        }

        public Matcher getMetcher() {
            return matcher;
        }

    }

}
