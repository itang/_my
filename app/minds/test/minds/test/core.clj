(ns minds.test.core
  (:use [minds.core]
        [midje.sweet]
        [cheshire.core]
        [hiccup.core :only (html)]))

(fact "mindmeister-to-freemind"
  (mindmeister-to-freemind nil) => empty-xml
  (mindmeister-to-freemind "") => empty-xml
  (mindmeister-to-freemind " ") => empty-xml
  (mindmeister-to-freemind "{}") => empty-xml)

(println (mindmeister-to-freemind
           (generate-string
             {"root"
              {"title" "我的语言"
               "children" [{"title" "clojure" "children" []}]}})))

(println (mindmeister-to-freemind
           (slurp "./test/minds/test/mindmeister.json")))

(write-mindmeister-to-freemind "./test/minds/test/mindmeister.json"
  "./test/minds/test/mindmeister.mm")
