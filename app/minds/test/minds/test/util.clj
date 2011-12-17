(ns minds.test.util
  (:use [minds.util])
  (:use [midje.sweet]))

;(fact "is-empty-string?"
;  (is-empty-string? nil) => true
;  (is-empty-string? "") => true
;  (is-empty-string? " ") => true
;  (is-empty-string? " \n") => true
;  (is-empty-string? " \n\r ") => true)

(fact "is-empty?"
  (is-empty? nil) => true
  (is-empty? "") => true
  (is-empty? " ") => true
  (is-empty? " \n") => true
  (is-empty? " \n\r ") => true
  (is-empty? "{title:'the title'}") => false
  (is-empty? '()) => true
  (is-empty? []) => true
  (is-empty? #{}) => true
  (is-empty? {}) => true)
