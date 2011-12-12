(ns prog_clojure.test.unifying-data-with-sequences
  (:use clojure.test
        midje.sweet))

(require '[clojure.string :as string])

(defn note-expected-failure [] (println "^^^^ The previous failure was expected ^^^^"))

(facts "construct-map" ;;
  (fact (hash-map :name "itang") => {:name "itang"}))

(facts "everything is a sequence"
  ;; nil seqable
  (fact (first nil) => nil)
  (fact (first []) => nil)

  ;; map seqable
  (fact (first {:name "itang"}) => [:name "itang"])
  (fact (first (array-map :name "itang" :age 18)) => [:name "itang"])

  ;; list seqable
  (fact (first '(1 2)) => 1)

  (fact (rest [1 2 3]) => [2 3])
  (fact (cons 1 [2 3 4]) => [1 2 3 4])
  (fact (cons [:age 30] {:name "itang"}) => '([:age 30] [:name "itang"])))

(facts "construct seq: use seq function"
  (fact (seq [1 2 3]) => [1 2 3])
  ;;(next aseq) is equuivalent to (seq (rest aseq)).
  (fact (seq (rest [1 2 3])) => (next [1 2 3]))
  (fact (rest (array-map :name "itang" :age 30)) => '([:age 30])))

(facts "sorted-set"
  (fact (first (sorted-set "b" "a")) => "a"))

(facts "sorted-map"
  (fact (first (sorted-map :name "itang" :age 30)) => [:age 30]))

(facts "conj: conj coll element & elements"
  (fact (conj '(1 2 3) :a ) => '(:a 1 2 3))
  (fact (conj [1 2 3] :a ) => [1 2 3 :a ]))

(facts "into: into to-coll from-coll"
  (fact (into '(1 2 3) '(:a )) => '(:a 1 2 3))
  (fact (into [1 2 3] [4 5]) => [1 2 3 4 5])
  (fact (into {:age 30} {:name "itang"}) => {:age 30 :name "itang"}))

(defn _echo [x] x)
(defn whole-numbers [] (iterate inc 1))

(facts "Creating Sequences"
  (facts "range: (range start? end step?)"
    (fact (pr-str (type (range 10))) => "clojure.lang.LazySeq")
    (fact (first (range 10)) => 0)
    (fact (last (range 10)) => 9)
    (fact (range 5) => '(0 1 2 3 4))
    (fact (range 10 11) => '(10))
    (fact (first (rest (range 1 15 2))) => 3)
    )
  (facts "repeat: repeat n x"
    (fact (repeat 5 1) => '(1 1 1 1 1))
    (fact (repeat 2 "x") => '("x" "x"))
    (fact (take 1 (repeat 1)) => '(1))
    )
  (facts "iterate: iterate f x"
    (fact (take 10 (iterate inc 1)) => (range 1 11)))

  (facts "take: take n sequence"
    (fact (take 20 (repeat 1)) => (take 20 (iterate _echo 1))))

  (facts "cycle: cycle coll"
    (fact (take 5 (cycle (range 3))) => '(0 1 2 0 1)))
  (facts "interleave: interleave & colls"
    (fact (interleave (whole-numbers) ["a" "b"]) => '(1 "a" 2 "b")))

  (facts "interpose: interpose separator coll"
    (fact (interpose "," ["apples" "bananas"]) => '("apples" "," "bananas"))
    (fact (string/join "," [1 2]) => "1,2"))

  (facts "create collect by function"
    (fact (list 1 2) => '(1 2))
    (fact (set [1 2]) => #{1 2})
    (fact (vector 1 2) => [1 2])
    (fact (hash-set 1 2) => #{1 2})
    (fact (hash-map :a 1 :b 2) => {:a 1 :b 2})
    (fact (vec (range 3)) => [0 1 2]))
  )

(facts "Filtering Sequences"
  (facts "filter: filter pred coll"
    (fact (take 5 (filter even? (whole-numbers))) => '(2 4 6 8 10))
    )
  (facts "take-while: take-while pred coll"
    (fact (take-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox") => '(\t \h))
    )
  (facts "drop-while: drop-while pred coll"
    (fact (drop-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox")
      => '(\e \- \q \u \i \c \k \- \b \r \o \w \n \- \f \o \x)
      ))
  (facts "split-at index coll, split-with f coll"
    (fact (split-at 5 (range 10)) => [(range 5) (range 5 10)])
    (fact (split-with even? (range 5)) => ['(0) '(1 2 3 4)])
    (fact (split-with #(<= % 3) (range 1 5)) => ['(1 2 3) '(4)]))
  )

(facts "Sequence Predicates"
  (facts "every? pred coll"
    (fact (every? odd? [1 3 5]) => true)
    (fact (every? odd? [1 3 5 6]) => false))
  (facts "some pred coll"
    (fact (some even? [1 2 3]) => true)
    (fact (some even? [1 3 5]) => nil)
    (fact (some identity [nil false 1 nil 2]) => 1)
    (fact (some _echo [nil false 1 nil 2]) => 1))

  (facts "not-every? pred coll/ not-any? pred coll"
    (fact (not-every? even? (whole-numbers)) => true)
    (fact (not-any? even? (whole-numbers)) => false)
    )
  )

(facts "Transforming Sequences"
  (facts "map f coll"
    (fact (map #(format "<p>%s</p>" %) ["the"]) => '("<p>the</p>")))
  (facts "(reduce f coll)"
    (fact (reduce + (range 1 11)) => 55))
  (facts "(sort comp? coll"
    (fact (sort [42 1 7]) => [1 7 42])
    (fact (sort-by #(.toString %) [1 7 42]) => [1 42 7])
    (fact (sort > [42 1 7]) => [42 7 1])
    (fact (sort-by :grade > [{:grade 83} {:grade 90} {:grade 77}])
      => '({:grade 90} {:grade 83} {:grade 77})
      ))
  )

(facts "list comprehension:
(for [binding-form coll-expr filter-expr? ...] expr)
"
  (fact (for [x [1 2 3]] (+ 1 x)) => [2 3 4])
  (fact (take 10 (for [n (whole-numbers) :when (even? n)] n)) => '(2 4 6 8 10 12 14 16 18 20))
  (fact (for [n (iterate inc 0) :while (even? n)] n) => '(0))
  )

(def x (for [i (range 1 3)] (do (println i) i)))
(facts "Lazy and Infinite Sequences"
  (fact (doall x) => '(1 2))
  (fact (dorun x) => nil)
  )

(import '(java.io File BufferedReader FileReader))
(doseq [x (seq (.listFiles (File. ".")))] (pr x))
(pr (map #(.getName %) (seq (.listFiles (File. ".")))))
(pr (map #(.getName %) (.listFiles (File. "."))))
(pr (count (file-seq (File. "."))))

(facts "Clojure Makes Java Seq-able"
  (fact (first (.getBytes "hello")) => 104)
  (fact (re-seq #"\w+" "the quick brown fox") => '("the" "quick" "brown" "fox"))
  (fact (map #(.getName %) (seq (.listFiles (File. "."))))
    =>
    (map #(.getName %) (.listFiles (File. "."))))
  )

(defn minutes-to-millis [mins] (* mins 1000 60))

(defn recently-modified? [file]
  (> (.lastModified file)
    (- (System/currentTimeMillis) (minutes-to-millis 30))))
(prn (filter recently-modified? (file-seq (File. "."))))

;;(prn (take 2 (line-seq (read (BufferedReader. (FileReader. (File. "project.clj")))))))

