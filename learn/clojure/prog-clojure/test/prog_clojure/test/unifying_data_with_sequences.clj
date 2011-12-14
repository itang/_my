(ns prog_clojure.test.unifying-data-with-sequences
  (:use clojure.test
        midje.sweet))

(require '[clojure.string :as string])

(defn note-expected-failure [] (println "^^^^ The previous failure was expected ^^^^"))

(fact "construct-map" ;;
  (hash-map :name "itang") => {:name "itang"})

(fact "everything is a sequence"
  ;; nil seqable
  (first nil) => nil
  (first []) => nil

  ;; map seqable
  (first {:name "itang"}) => [:name "itang"]
  (first (array-map :name "itang" :age 18)) => [:name "itang"]

  ;; list seqable
  (first '(1 2)) => 1

  (rest [1 2 3]) => [2 3]
  (cons 1 [2 3 4]) => [1 2 3 4]
  (cons [:age 30] {:name "itang"}) => '([:age 30] [:name "itang"]))

(fact "construct seq: use seq function"
  (seq [1 2 3]) => [1 2 3]
  ;;(next aseq) is equuivalent to (seq (rest aseq)).
  (seq (rest [1 2 3])) => (next [1 2 3])
  (rest (array-map :name "itang" :age 30)) => '([:age 30]))

(fact "sorted-set"
  (first (sorted-set "b" "a")) => "a")

(fact "sorted-map"
  (first (sorted-map :name "itang" :age 30)) => [:age 30])

(fact "conj: conj coll element & elements"
  (conj '(1 2 3) :a ) => '(:a 1 2 3)
  (conj [1 2 3] :a ) => [1 2 3 :a ])

(fact "into: into to-coll from-coll"
  (into '(1 2 3) '(:a )) => '(:a 1 2 3)
  (into [1 2 3] [4 5]) => [1 2 3 4 5]
  (into {:age 30} {:name "itang"}) => {:age 30 :name "itang"})

(defn _echo [x] x)
(defn whole-numbers [] (iterate inc 1))

(facts "Creating Sequences"
  (fact "range: (range start? end step?)"
    (pr-str (type (range 10))) => "clojure.lang.LazySeq"
    (first (range 10)) => 0
    (last (range 10)) => 9
    (range 5) => '(0 1 2 3 4)
    (range 10 11) => '(10)
    (first (rest (range 1 15 2))) => 3)

  (fact "repeat: repeat n x"
    (repeat 5 1) => '(1 1 1 1 1)
    (repeat 2 "x") => '("x" "x")
    (take 1 (repeat 1)) => '(1))

  (fact "iterate: iterate f x"
    (take 10 (iterate inc 1)) => (range 1 11))

  (fact "take: take n sequence"
    (take 20 (repeat 1)) => (take 20 (iterate _echo 1)))

  (fact "cycle: cycle coll"
    (take 5 (cycle (range 3))) => '(0 1 2 0 1))

  (fact "interleave: interleave & colls"
    (interleave (whole-numbers) ["a" "b"]) => '(1 "a" 2 "b"))

  (fact "interpose: interpose separator coll"
    (interpose "," ["apples" "bananas"]) => '("apples" "," "bananas")
    (string/join "," [1 2]) => "1,2")

  (fact "create collect by function"
    (list 1 2) => '(1 2)
    (set [1 2]) => #{1 2}
    (vector 1 2) => [1 2]
    (hash-set 1 2) => #{1 2}
    (hash-map :a 1 :b 2) => {:a 1 :b 2}
    (vec (range 3)) => [0 1 2]))

(facts "Filtering Sequences"
  (fact "filter: filter pred coll"
    (take 5 (filter even? (whole-numbers))) => '(2 4 6 8 10))

  (fact "take-while: take-while pred coll"
    (take-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox") => '(\t \h))

  (fact "drop-while: drop-while pred coll"
    (drop-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox")
    => '(\e \- \q \u \i \c \k \- \b \r \o \w \n \- \f \o \x))

  (fact "split-at index coll, split-with f coll"
    (split-at 5 (range 10)) => [(range 5) (range 5 10)]
    (split-with even? (range 5)) => ['(0) '(1 2 3 4)]
    (split-with #(<= % 3) (range 1 5)) => ['(1 2 3) '(4)]))

(facts "Sequence Predicates"
  (fact "every? pred coll"
    (every? odd? [1 3 5]) => true
    (every? odd? [1 3 5 6]) => false)

  (fact "some pred coll"
    (some even? [1 2 3]) => true
    (some even? [1 3 5]) => nil
    (some identity [nil false 1 nil 2]) => 1
    (some _echo [nil false 1 nil 2]) => 1)

  (fact "not-every? pred coll/ not-any? pred coll"
    (not-every? even? (whole-numbers)) => true
    (not-any? even? (whole-numbers)) => false))

(facts "Transforming Sequences"
  (fact "map f coll"
    (map #(format "<p>%s</p>" %) ["the"]) => '("<p>the</p>"))

  (fact "(reduce f coll)"
    (reduce + (range 1 11)) => 55)

  (fact "(sort comp? coll"
    (sort [42 1 7]) => [1 7 42]
    (sort-by #(.toString %) [1 7 42]) => [1 42 7]
    (sort > [42 1 7]) => [42 7 1]
    (sort-by :grade > [{:grade 83} {:grade 90} {:grade 77}]) => '({:grade 90} {:grade 83} {:grade 77})))


(fact "list comprehension:
(for [binding-form coll-expr filter-expr? ...] expr)
"
  (for [x [1 2 3]] (+ 1 x)) => [2 3 4]
  (take 10 (for [n (whole-numbers) :when (even? n)] n)) => '(2 4 6 8 10 12 14 16 18 20)
  (for [n (iterate inc 0) :while (even? n)] n) => '(0))

(def x (for [i (range 1 3)] (do (println i) i)))
(fact "Lazy and Infinite Sequences"
  (doall x) => '(1 2)
  (dorun x) => nil)

(import '(java.io File BufferedReader FileReader))
(doseq [x (seq (.listFiles (File. ".")))] (pr x))
(pr (map #(.getName %) (seq (.listFiles (File. ".")))))
(pr (map #(.getName %) (.listFiles (File. "."))))
(pr (count (file-seq (File. "."))))

(fact "Clojure Makes Java Seq-able"
  (first (.getBytes "hello")) => 104
  (re-seq #"\w+" "the quick brown fox") => '("the" "quick" "brown" "fox")
  (map #(.getName %) (seq (.listFiles (File. ".")))) => (map #(.getName %) (.listFiles (File. "."))))

(defn minutes-to-millis [mins] (* mins 1000 60))

(defn recently-modified? [file]
  (> (.lastModified file)
    (- (System/currentTimeMillis) (minutes-to-millis 30))))
(prn (filter recently-modified? (file-seq (File. "."))))

;;(prn (take 2 (line-seq (read (BufferedReader. (FileReader. (File. "project.clj")))))))

