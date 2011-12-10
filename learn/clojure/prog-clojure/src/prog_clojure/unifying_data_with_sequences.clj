(ns prog-clojure.unifying-data-with-sequences
  "clojure 数据结构 序列:
  structures such as strings, lists, vectors, maps, sets, and trees")

(prn "string:" "test")
(prn "list:" '(1 2 3))
(prn "vector:" [1 2 3])
(prn "map:" {:no 1})
(prn "set:" #{1 2 3})
(prn "set from vector:" (set [1 2 3 3 4 4 2]))
(prn "to seq:" (seq #{1 2 3}))

(println "In clojure, all there data structures can be accessed through a single abstraction:
         the sequence(or seq)"
  "A seq is a logical list"
  )

(println "Collections that can be viewed as seqs are called seq-able (pronounced
“SEEK-a-bull”).
")





