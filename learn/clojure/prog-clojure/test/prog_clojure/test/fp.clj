(ns prog-clojure.test.fp
  (:use midje.sweet))

(defn stack-consuming-fibo [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (stack-consuming-fibo (- n 1))
            (stack-consuming-fibo (- n 2)))))

(defn fibs [^Integer x]
  (loop [ret [] i 0]
    (if (> i x)
      ret
      (recur (conj ret (stack-consuming-fibo i)) (inc i)))))

(defn fibs1 [x]
  (map stack-consuming-fibo (range 0 (+ 1 x))))

(fact "stack-consuming-fibo"
  (stack-consuming-fibo 0) => 0
  (stack-consuming-fibo 1) => 1
  (stack-consuming-fibo 2) => 1
  (stack-consuming-fibo 3) => 2)

(fact "fibs"
  (fibs 2) => '(0 1 1))

(fact "fibs"
  (fibs1 2) => '(0 1 1)
  (fibs1 4) => '(0 1 1 2 3))

