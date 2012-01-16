;; Macros give clojure great power. With most programming techiques,
;; you build features within the language, when you writge macros, it
;; is more accurate to say that you are "adding features to " the
;; language. This is powerful and dangerous ability, so you should
;; follow the rules in Section 7.1. When to use macros, atleast until
;; you have enough experience to decide for yourself when to bend the
;; rules.

(defn unless [expr form]
  (if expr nil form))

(unless true (println "true "))
(unless false (println "false"))

;; macros solve this problem, because the do not evaluate their
;; arguments immediately. Instead, you get to choose when (and if )
;; the argumetns to a macro are evaluated.

;; When clojure encounters a macro, it processess it in two
;; steps.First, it expands (executes ) the macro and substitutes the
;; result back into the program. this is called

;; "macro expansion time".

;; Then it continues with the normal compile time.

;; To write unless , you need to write Clojure code to perform the
;; follwoing translation at macro expansion time:

;; (unless expr form) -> (if expr nil form)
;; then, you need to tell clojure that your code is a macroby using
;; defmacro, which looks almost like defn:

;; (defmacro name doc-string? attr-map? [parmas*] body)

;; because clojure code is just clojure data, you already have all the
;; tools you need to write unless. Write the unless macro using list
;; to build the if expression:

(defmacro unless [expr form]
  (list 'if expr nil form))

;; The body of unless executes at macro expansion time, producing an
;; if form for compilation. If you enter this expression at the REPL:

(unless false (println "this should print"))

;;then clojure will (invisbly to you ) expand the unless form into:
(if false nil (println "this should print"))
;; then clojure compiles and executes the expanded if form. Verify
;; that unless works correctly for both true and false:

(unless true (println "this should not print"))

;; #Special Forms, Design Paterns, and Macros
;; clojure has nospecial syntax for code. code is composed of data
;; structures. This is true for normal functions but also for special
;; forms and macros

;; Consider a language with more syntactic variety. such as Java. in
;; java, the most flexible mechanism for writing code is the instance
;; method. Imagine that you are writing a Java program. If you
;; discover a recurring pattern in some instance methods, you have the
;; entire Java lanauage a t your disposal to encapsulate that
;; recurring pattern.,

;; good so far, but java also has lots of "specialforms (although they
;; are not normally called by that name).Unlike clojure special forms
;; ,which are jsut clojure data. each Java Special form has its own
;; sysntax. For example , if is a special form in Java. If you
;; discover a recurringpattern of usage in voling if, there is no way
;; to encapsulate that pattern, you cannnot create an unless, so you
;; are stuck simulating unless with an idiomatic usage of if:

;; thewikipedia defines a design pattern to be a "general reusable
;; solution to a commonly occurring problem in software desigin"

;; # Expanding Macros
;; when you created the unless macro,you quoted the symbol if:
;; But you don't quote any other symbols. To understand why, you need
;; to think carefully about what happends at macro expansion time:
;; By quoting 'if, you prevent clojure from directly devaluating if at
;; macro expansion time. Instead , evaluation strips off the quote,
;; leaving if to be compiled.
;; you dont=ot want to quote expr and form , because the are macro
;; arguments. Clojure will substitute them without evaluation at macro
;; expansion time

;; you don't need to quote nil, since nil evaluates to itself.

;; thinking about what needs to be quoted can get compilcated quickly
;; . Fortunately, you do not have to do this work in your head.
;; Clojure includes diagnostic functions so that you can test macro
;; expansions at the REPL.
;; the function macroexpand-1 will show you what happens at macro
;; expansion time:
;; macroexpand-1 form)

(def flag true)
(macroexpand-1 '(unless flag "ss"))

;; you are only expanding them, not attempting to compile and execute
;; them.

;; another recursive macro is and. if you call and with more than two
;; arguments. it will expand to include another call to and, with one
;; less argument:
(macroexpand '(and 1 2 3))

(defmacro when-not [test & body]
  (list 'if test nil (cons 'do body)))
(macroexpand-1 '(when-not false (println "1") (println "2")))

;; #make macro simpler
;; the unless macro is a great simple example, but most macros are
;; more complex. In this section, we will build a set of increasingly
;; complex macros, introducing clojure features as we go. Foryou
;; reference , the features introduced in this section are summrized
;; in Figure 7.1.
;; First ,let's build a replica of clojure's macro. we'll call it
;; chain, since it chains a series of method calls. Here are some
;; sample expansions of chain.

(defmacro chain
  ([x form] (list '. x form))
  ([x form & more] (concat (list 'chain (list '. x form)) more)))

;; too# auto-gensym: inside a syntax quoted section, create a unique
;; name prefixed with foo.

;; (gensym prefix?) create a unqique name, with optaion prefix.
;; (macroexpand form) expand form with macroexpand-l repeatedly until
;; the returned form is no longer a macro
;;(macroexpand-l form) show how clojure will expand form.
;; (list-flag? ~@form list-frag?) splicing unquote: Use inside a
;; syntax-quote to splice an unquoted list into a template.
;; `form syntax quote: Quote form, but allow internal unquoting so
;; that form acts a template.Symbols inside form are resolved to help
;; prevent inadventent symbol capture.
;; ~form unquote : Use inside a syntax-quote to substite an unquoted value.

;; (defmacro chain [x form]
;;  `(. ~x ~form))

(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] `(chain (. ~x ~form) ~@more)))

(macroexpand '(chain arm getHand getFinger))

;; Many macros follow the pattern of chain , aka clojure ..:
;; 1. Begin the macro body with a syntax quote (`) to treat the entire
;; thing as a template.
;;2 Insert invividual arguments with an unquote(~)
;;3 splice in more arguments with splicing unquote (~@).
;; the macros we have built to so far been simple enough to avoid
;; creating any bindings with let or binding. Let's create such a
;; macro next.

;; clojure provide a reader form for creating unique local
;; names.Inside a syntax-quoted form, you can append an octothorpe(#)
;; to an unqualified name, and clojure will create an autogenerated
;; symbol, or autogensym: a ysmbol based on the name plus an
;; underscore and a niqure ID, try it at the REPL.

'foo#

(defmacro bench [expr]
  `(let [start# (System/nanoTime)
         result# ~expr]
     {:result result# :elapsed (- (System/nanoTime) start#)}))

(macroexpand '(bench (str "a" "b")))
(bench (str "a" "b"))

;; Special forms provide the most basic flow contgroll structures,such
;; as if and recur. All flow contgrol macros must eventually canll
;; special form

;; special Forms provide direct access to Java. Whenever you call Java
;; form clojure, you are going through at least one special form, such
;; as the dot or new.

;; Names are created and bound through special forms, whether defineg
;; a var with def , creating a lexical binding with let, or creating a
;; dynamic binding with binding.

(defmacro my-and
  ([] true)
  ([x] x)
  ([x & rest]
     `(let [and# ~x]
        (if and# (and ~@rest) and#))))

(my-and 1 2)

(defmacro defstruct
  [name & keys]
  `(def ~name (create-struct ~@keys)))


;; #Java Interop
;; CLojure programscall into Java via the . (dot), new , and set!
;; special forms. However,idiomatic clojure code often uses macos such
;; as ..(threaded memebr access) adn doto to simplify forms that call
;; Java.

;; delay force
;; (delay & exprs)
;; (force x)

(def slow-calc (delay (Thread/sleep 500) "done!"))
(force slow-calc)

(with-out-str (print "hello,") (print "world"))
