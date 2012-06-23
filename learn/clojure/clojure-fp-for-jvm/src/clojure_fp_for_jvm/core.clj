(ns clojure-fp-for-jvm.core)

(def special-forms
  ["catch" "def" "do" "dot" "finally" "fn" "if" "let"
   "loop" "monitor-enter" "monitor-exit" "new" "quote" "recur" "set!" "throw" "try" "var"])

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!")
  (print "special-forms total size:" (count special-forms) "\n")
  (doseq [sf special-forms]
    (println sf))
  (println "Refs 通过使用Software Transactional Memory (STM)来提通对于多块共享数据的同步访问")
  (println "Atoms 提供对于单个共享数据的同步访问")
  (println "Agents 提供对于单个共享数据的异步访问")
  (println "Symbols 是用来给东西命名的。这些名字是被限制在名字空间里的， 要么是指定的名字空间，要么是当前的名字空间")
  (println "Symbols的值是它所代表的名字的值。要使用sysmbol的值，你必须把它用引号引起来")
  (println "lazy-seq 可以是一些列计算值的过程")
  (let [[a b c] [1 23 4]] (println a))
  (println "在事务里执行的代码一定要是没有副作用的， 因为事务可能会跟别的事务冲突， 然后重试，如果有副作用的话， 那么结果就可能不对了")
  )
