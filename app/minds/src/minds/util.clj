(ns minds.util
  (:use [clojure.string :only (blank?)]))

;(defn is-empty-string?
;  [s]
;  (cond
;    (nil? s) true
;    (string? s) (or (= "" s) (not (nil? (re-find #"\s+" s))))
;    :else false))

(defn empty-x? [obj]
  "判定值是否为空"
  (cond
    (nil? obj) true
    (string? obj) (blank? obj) ;; blank?
    (char? obj) (blank? (str obj))
    (coll? obj) (empty? obj) ;; empty? coll
    :else false))

(defmacro if-empty [cond ept-expr els-expr]
  `(if (empty-x? ~cond) ~ept-expr ~els-expr))

(defmacro empty-else [obj default-value-expr]
  `(if-empty ~obj ~default-value-expr ~obj))

