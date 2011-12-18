(ns minds.test.util
  (:use [minds.util])
  (:use [midje.sweet]))

;(fact "is-empty-string?"
;  (is-empty-string? nil) => true
;  (is-empty-string? "") => true
;  (is-empty-string? " ") => true
;  (is-empty-string? " \n") => true
;  (is-empty-string? " \n\r ") => true)

(fact "empty-x?"
  (empty-x? nil) => true
  (empty-x? "") => true
  (empty-x? " ") => true
  (empty-x? " \n") => true
  (empty-x? " \n\r ") => true
  (empty-x? \space) => true

  (empty-x? '()) => true
  (empty-x? []) => true
  (empty-x? #{}) => true
  (empty-x? {}) => true

  (empty-x? "{title:'the title'}") => false
  (empty-x? \a) => false
  (empty-x? {:name "itang"}) => false
  (empty-x? 'str) => false
  (empty-x? :keyname ) => false)

(fact "if-empty"
  (if-empty nil "empty" "some") => "empty"
  (if-empty " \n" "empty" "some") => "empty"
  (if-empty {} "empty" "some") => "empty"

  (if-empty "hello" "empty" "some") => "some"
  (if-empty {:name "itang"} "empty" "some") => "some")

(fact "empty-else"
  (empty-else nil "some") => "some"
  (empty-else {} "some") => "some"
  (empty-else "helloworld" "some") => "helloworld")
