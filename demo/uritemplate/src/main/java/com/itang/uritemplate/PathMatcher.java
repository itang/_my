package com.itang.uritemplate;

import java.util.Map;

public interface PathMatcher {
    public boolean isMatched();

    public Map<String, String> getVariables();
}
