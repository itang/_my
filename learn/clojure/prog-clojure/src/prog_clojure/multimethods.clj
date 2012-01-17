;; # Multimethods

;; clojure multimethods provide a flexible way to associate a function with a set of inputs. This is similar to Java polymorphism but more general. when ou call a Java method, Java selects a specific implemetation to execute by examining the type of a single object. When you call a clojre multimethod, Clojure selects a specific implementation to execute by examinging the result of any function you choose, applied to all the functions's argumetns.

;; in this chapter, you will develop a thirst for multimethods by first living without them. Then you will build an increasingly complex series of multimethods implementations. First , you will use multimethods to simulate polymorphism. Then ,you will use multimethods to implement various ad hoc taxonomies.

;; ## live without multimethods
;; the best way to appreciate multimethods is to apend a few minutes living without them.

(defn my-print [ob]
  (cond
   (nil? ob) (.write *out* "nil")
   (string? ob) (.write *out* ob)))

(defn my-println [ob]
  (my-print ob)
  (.write *out* "\n"))

(my-print "hello")
(my-print nil)

;; what you really want is a way to add new features to the system by
;; adding new code in a single place, without having to modify any
;; existing code. The solution, of course, is multimethods.

;; Defining Multimethods
;; (defmulti name dispatch-fn)

;; name is the name of the new multimethod, and clojure will invoke
;; dispatch-fn against the method arguments to select one particular
;; method of the multimethod.

;; Consider my-print from the previous section, it takes a single
;; argument, the thing to be printed ,and you want to select a
;; specific implementation based on the type of that argument. so ,
;; dispatch-fn needs to be a function of one argument that returns the
;; type of that argument. Clojure has a built-in function matching
;; this description, namely , class. Use class to create a multimethod
;; called my-print:

(defmulti my-print class)

(try (my-print "foo")
     (catch Exception e (println (.getMessage e))))

;; to add a specific method imlementation to my-println, use
;; defmethod:
;; (defmethod name dispatch-val & fn-tail)

(defmethod my-print String [s]
  (.write *out* s))

(defmethod my-print nil [s]
  (.write *out* "nil"))

(defmethod my-print Number [s]
  (.write *out* (.toString s)))

(my-print "stu")
(my-print nil)
(my-print 42)

;; Mutimethod Defaults
;; it would be nice if my-point could have a fallback reresentaton
;; that you could use for any type you have not specifically defined.
;; You can use :default as a dispatch value to handle any methods that
;; do ot match anything more specific, Using :default, create a
;; my-println that prints the Java toString value of Objects, wrapped
;; in #<>:

(defmethod my-print :default [s]
  (doto *out*
    (.write "#<")
    (.write (.toString s))
    (.write ">")))

(my-print (java.sql.Date. 0))
(my-print (java.util.Random.))

;; (defmulti name dispatch-fn :default default-value)
(defmulti my-println class :default :everything-else)
(defmethod my-println String [s]
  (.write *out* s))
(defmethod my-println :everything-else [_]
  (.write *out* "Not implemented yet ..."))

(my-println (java.util.Date.))
(my-println nil)

(use '[clojure.string :only (join)])
(defmethod my-print java.util.Collection [c]
  (.write *out* "(")
  (.write *out* (join " " c))
  (.write *out* ")"))

(my-print (take 6 (cycle [1 2 3])))

(defmethod my-print clojure.lang.IPersistentVector [c]
  (.write *out* "[")
  (.write *out* (join " " c))
  (.write *out* "]"))

(prefer-method my-print clojure.lang.IPersistentVector java.util.Collection)

(my-print [1 2 3])

;; Many languages create complex rules, or arbitrary limitations, in
;; order to resolve ambiguities in their systems for dispatching
;; functions, clojure allow a much simpler approach: Just don't worry
;; about it! if there is an ambiguity, use prefer-method to resovle
;;it.

;; Creating Ad Hoc Taxonomies
;;

;; Clojure lets you define arbitray parent/child relationships with
;; derive:
;; (derive child parent)

(derive ::a  ::b)

(isa? ::a ::b)
