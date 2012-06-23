(ns clj-require-test.core
  (:require clj-require-test.m)
  (:require clj-require-test.n))

(defn reload-ns [^String name] 
  (require (symbol name) :reload-all))

(defn -main
  [& args]
  (reload-ns "clj-require-test.m")
  (clj-require-test.n/your-name))