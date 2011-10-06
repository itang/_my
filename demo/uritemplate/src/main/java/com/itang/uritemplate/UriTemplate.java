package com.itang.uritemplate;

import java.util.List;
import java.util.Map;

/**
 * URI 模板.
 */
public interface UriTemplate {
    /**
     * 获取uri匹配模式.
     * <p/>
     * uri匹配模式组成规则:
     * <ul>
     * <li>不能为null</li>
     * <li>路径变量以{}包含,可以包含的字母同java变量</li>
     * <li>路径变量不允许同名</li>
     * <li>路径变量不能跨'/'</li>
     * <li>以'/'打头.如果未以'/'打头, 自动加上</li>
     * <li>不以'/'结尾.如果以'/'结尾, 自动去掉</li>
     * </ul>
     */
    public String getPattern();

    /**
     * 获取路径变量列表.
     */
    public List<String> getVariableNames();

    /**
     * 是否匹配指定的URI.
     *
     * @param uri 待匹配的uri, '/'开头, 不包含上下文路径和查询参数, 如 /user/handsomes/tomas
     */
    public boolean matches(String uri);

    /**
     * 匹配指定URI并返回类型为Matcher的匹配信息.
     *
     * @param uri 待匹配的uri
     */
    public PathMatcher matcher(String uri);

    /**
     * 获取URI.
     *
     * @param variables 变量值
     * @return 转换后的URI
     */
    public String toURI(Map<String, Object> variables);
}
