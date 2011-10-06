package com.itang.uritemplate.impl;


import com.itang.uritemplate.PathMatcher;

import java.util.Collections;
import java.util.Map;

class PathMatchers {
    public static PathMatcher NON_MATCHER = new PathMatcher() {
        public boolean isMatched() {
            return false;
        }

        public Map<String, String> getVariables() {
            return Collections.emptyMap();
        }
    };

    public static PathMatcher matched(final Map<String, String> variable) {
        return new PathMatcher() {
            public boolean isMatched() {
                return true;
            }

            public Map<String, String> getVariables() {
                return variable;
            }
        };
    }
}
