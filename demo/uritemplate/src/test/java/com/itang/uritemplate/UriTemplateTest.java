package com.itang.uritemplate;

import com.itang.uritemplate.impl.UriTemplateImpl;
import org.junit.Assert;
import org.junit.Test;

public class UriTemplateTest extends Assert {

    /**
     * 构造测试.
     */
    @Test
    public void test_constructor() {
        uriTemplates("/user");// 正常参数
        uriTemplates("");// 允许空字符

        try {
            uriTemplates(null);// 不接受pattern是null
            fail();
        } catch (Exception e) {
            // ignore
        }
    }

    @Test
    public void test_pattern() {
        uriTemplates("/user/{id}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertEquals("/user/{id}", uriTemplate.getPattern());
            }
        });

        // 如果pattern没有以'/'打头, 确定加上
        assertEquals("/", uriTemplate("").getPattern());
        assertEquals("/user", uriTemplate("user").getPattern());

        // 不以'/'结尾
        assertEquals("/user", uriTemplate("user/").getPattern());
        assertEquals("/user", uriTemplate("user//").getPattern());
        assertEquals("/user/{id}",
                new UriTemplateImpl("/user/{id}/").getPattern());

        assertEquals("/user/{id}",
                new UriTemplateImpl("//user///{id}//").getPattern());
        assertEquals("/user/{id}",
                new UriTemplateImpl("/user//{id}").getPattern());
    }

    @Test
    public void test_get_variable_names() {
        uriTemplates("/user/", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.getVariableNames().isEmpty());
            }
        });

        uriTemplates("/user/{id", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.getVariableNames().isEmpty());
            }
        });

        uriTemplates("/user/{id}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertEquals("id", uriTemplate.getVariableNames().get(0));
            }
        });

        uriTemplates("/user/{category}/{name}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertEquals("category", uriTemplate.getVariableNames().get(0));
                assertEquals("name", uriTemplate.getVariableNames().get(1));
            }
        });
    }

    @Test
    public void test_matches() {
        // 忽略最尾端 /
        uriTemplates("/", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/"));
                assertTrue(uriTemplate.matches(""));
            }
        });
        uriTemplates("/user/", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/user/"));
                assertTrue(uriTemplate.matches("/user"));

                assertFalse(uriTemplate.matches("/user1"));
                assertFalse(uriTemplate.matches("/user/tomas"));
            }
        });

        uriTemplates("/user/{name}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/user/"));
                assertTrue(uriTemplate.matches("/user/1000"));
                assertTrue(uriTemplate.matches("/user/hello"));
            }
        });

        uriTemplates("/user/{category}/{name}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/user"));
                assertTrue(uriTemplate.matches("/user/"));
                assertTrue(uriTemplate.matches("/user/tomas"));
                assertTrue(uriTemplate.matches("/user/handsome/tomas"));
                assertTrue(uriTemplate.matches("/user/handsome/tomas/"));

                assertFalse(uriTemplate.matches("/"));
                assertFalse(uriTemplate.matches("/usr"));
                assertFalse(uriTemplate.matches("/user/handsome/tomas/1"));
            }
        });

        uriTemplates("/user/man-{category}/{name}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/user/man-"));
                assertTrue(uriTemplate.matches("/user/man-/"));
                assertTrue(uriTemplate.matches("/user/man-handsome/"));
                assertTrue(uriTemplate.matches("/user/man-handsome/tomas"));
                assertTrue(uriTemplate.matches("/user/man-handsome/tomas/"));

                assertFalse(uriTemplate.matches("/"));
                assertFalse(uriTemplate.matches("/user"));
                assertFalse(uriTemplate.matches("/user/man-handsome/tomas/1"));
            }
        });

        uriTemplates("/user/{category}/{name}.{format}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/user/handsome/tomcas.xml"));

                assertTrue(uriTemplate.matches("/user/handsome/tomcas.html"));

                //'.'号不能忽略 TODO 改变此规则?
                assertTrue(uriTemplate.matches("/user/handsome/tomcas."));

                assertFalse(uriTemplate.matches("/user/handsome/tomcas"));
            }
        });

        uriTemplates("/note/{id}/#c-{subid}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                assertTrue(uriTemplate.matches("/note/#c-"));
                assertTrue(uriTemplate.matches("/note/107613165/#c-15277983"));

                assertFalse(uriTemplate.matches("/note/107613165/#bad-15277983"));
                assertFalse(uriTemplate.matches("/note/107613165/#bad-15277983/more"));
            }
        });


    }


    @Test
    public void test_matcher() {
        uriTemplates("/user/{category}/{name}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                PathMatcher pathMatcher = uriTemplate.matcher("/user/handsome/tomas");
                assertTrue(pathMatcher.isMatched());
                assertEquals(2, pathMatcher.getVariables().size());
                assertEquals("handsome", pathMatcher.getVariables().get("category"));
                assertEquals("tomas", pathMatcher.getVariables().get("name"));

                pathMatcher = uriTemplate.matcher("/user/handsome/");
                assertTrue(pathMatcher.isMatched());
                assertEquals(2, pathMatcher.getVariables().size());
                assertEquals("handsome", pathMatcher.getVariables().get("category"));
                assertEquals(null, pathMatcher.getVariables().get("name"));

                pathMatcher = uriTemplate.matcher("/user//");
                assertTrue(pathMatcher.isMatched());
                assertEquals(2, pathMatcher.getVariables().size());
                assertEquals(null, pathMatcher.getVariables().get("category"));
                assertEquals(null, pathMatcher.getVariables().get("name"));

                pathMatcher = uriTemplate.matcher("/user/handsome/tomas/1");
                assertFalse(pathMatcher.isMatched());
                assertEquals(0, pathMatcher.getVariables().size());
            }
        });

        uriTemplates("/user/{v1}/{v2}/{v3}/{v4}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                PathMatcher pathMatcher = uriTemplate.matcher("/user/v1/v2/v3/v4");
                assertTrue(pathMatcher.isMatched());
                assertEquals(4, pathMatcher.getVariables().size());
            }
        });

        uriTemplates("/user/{category}/{name}.{format}", new Callable() {
            public void with(UriTemplate uriTemplate) {
                PathMatcher pathMatcher = uriTemplate.matcher("/user/handsome/tomas.xml");
                assertTrue(pathMatcher.isMatched());
                assertEquals(3, pathMatcher.getVariables().size());
                assertEquals("xml", pathMatcher.getVariables().get("format"));
            }
        });
    }

    static interface Callable {
        public void with(UriTemplate uriTemplate);
    }

    private void uriTemplates(String pattern, Callable call) {
        for (UriTemplate ut : uriTemplates(pattern)) {
            call.with(ut);
        }
    }


    private UriTemplate uriTemplate(String pattern) {
        return uriTemplates(pattern)[0];
    }

    private UriTemplate[] uriTemplates(String pattern) {
        UriTemplate[] uriTemplates = {new UriTemplateImpl(pattern)};
        return uriTemplates;
    }

}
