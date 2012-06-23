(ns clj-require-test.m)

(def a
  (do (println "declare a" ":" (System/currentTimeMillis))
      "helloword"))