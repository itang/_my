(ns minds.util
  (:use [clojure.string :only (blank?)]))

;(defn is-empty-string?
;  [s]
;  (cond
;    (nil? s) true
;    (string? s) (or (= "" s) (not (nil? (re-find #"\s+" s))))
;    :else false))

(defn is-empty? [obj]
  (cond
    (nil? obj) true
    (string? obj) (blank? obj)
    (map? obj) (= {} obj)
    (vector? obj) (= [] obj)
    (list? obj) (= '() obj)
    (set? obj) (= #{} obj)
    :else false))