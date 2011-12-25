(ns prog-clojure.test.lazy-seq
  (:use midje.sweet))

;;Lazy Sequences
;(lazy-seq & body)
;; lazy-seq will invoke its body only when needed, that is, when seq is
;;called directly or indirectly. lazy-seq will then cache the result for sub-
;;sequent calls. You can use lazy-seq to define a lazy Fibonacci series as
;;follows:
(defn lazy-seq-fibo
  ([]
    (concat [0 1] (lazy-seq-fibo 0 1)))
  ([a b]
    (let [n (+ a b)]
      (lazy-seq (cons n (lazy-seq-fibo b n))))))

(fact
  (take 2 (lazy-seq-fibo)) => '(0 1))


(declare my-odd? my-even?)

(defn my-odd? [n]
  (if (= n 0)
    false
    (my-even? (dec n))))
(defn my-even? [n]
  (if (= n 0)
    true
    (my-odd? (dec n))))
(fact
  (map my-even? (range 10)) =>
  '(true false true false true false true false true false))

(defn trampoline-fibo [n]
  (let [fib
        (fn fib-it [f-2 f-1 current]
          (let [f (+ f-2 f-1)]
            (if (= n current)
              f
              #(fib-it f-1 f (inc current)))))]
    (cond
      (= n 0) 0
      (= n 1) 1
      :else (fib 0 1 2))))

;(rem (trampoline trampoline-fibo 1000000) 1000) => 875
(fact
  (trampoline trampoline-fibo 9) => 34)


