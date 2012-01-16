(ns pclojure.core)

;;(ref initial-state options*)
;; options include:
;; :validator validate-fn
;; :meta metadata-map

(def validate-message-list
  (partial every? #(and (:sender %) (:text %))))

(def messages (ref () :validator validate-message-list))

(defn add-message [message]
  (dosync (alter messages conj message)))

;;/ (add-message "not a valid message") ;;  error: java.lang.IllegalStateException: Invalid reference state

(add-message {:sender "itang" :text "Hello"})

(doseq [m @messages]
  (println m))

;;Atoms do not participate in transactions and thus do not require
;;dosync. To set the value of an atom , simple call reset!

(def current-track (atom "Venus"))
(deref current-track)
(reset! current-track "Credo")

;;What if you want to coordinate an update of both current-track and
;;current-composer with an atom ? the short answer is "You can't."
;;That is the difference between refs and atoms. If you need
;;coordinated access, use a ref.
;;如果你想在更新t和c的时候，进行协作通过atom，简单的回答就是“你不能”，
;;这就是refs和atoms的区别。如果你需要协同访问，那么请请使用ref

;; Maybe you like to listen to serveral tracks in a row by the same
;; commposer. If so, you want to change the track title but keep the
;; same composer, swap! will do the trick:
;;; (swap! an-atom f & args)

(def current-track (atom {:title "Gred" :composer "Byrd"}))
(reset! current-track {:title "Spe" :composer "Tal"})
(swap! current-track assoc :title "Sancte ")

;; swap! returns the new value. Calls to swap! might be retried, if
;; other threads are attempting to modify the same atom. So , the
;; function you pass to swap! should have no side effects.

;; Both refs and atos perform sysnchronous updates. When the upate
;; function returns, the value is already changed. if you do not need
;; this level of control and can tolerate updates happening
;; asynchronously at some later time,  prefer an agent.

;; # Use Agents for Asynchronous Updates
;; Some applications have tasks that can proceed independently with
;; minimal coordination between tasks. Clojure agents support this
;; style of task.

;; Agents have much in common with refs. Like refs, you create an
;; agent by wrapping some piece of initail state:
;;    (agent initial-state)
;; Create a counter agent that wraps an initial count of zero:

(def counter (agent 0))

;; Once you have an agent, you can send the agent a function to update
;; its state. send queues an update-fn to run later, on a thread in a
;; thread pool:
;;    (send agent update-fn & args)

;; Sending to an agent is very much like commuting a ref. Tell the
;; counter to inc:

(send counter inc)
(println @counter)

;; if the race condition between the REPL and the agent thread bothers
;; you , there is a solution. If you wnat to be sure that the agent
;; has completed the actions you sent to it, you can call await or
;; await-for:
;;    (await & agents)
;;    (await-for timeout-millis & agents)

(def counter (agent 0 :validator number?))
;;(send counter (fn [_] "boo")) ;;  [Thrown class
;;java.lang.IllegalStateException]

(defn double [X] (* 2 X))
(send counter inc)
(send counter double)
(send counter (partial * 10))
(println @counter)

;; To discover the specific error (or errors), call agent-errors,
;; which will return a sequence of errors thrown during agent actions:
;;    (agent-error counter)
;;    (clear-agent-errors agent)

;; #Including Agents in transactions
;; Transactions should not have side effects, because Clojure may
;; retry a transaction an arbitry number of times. However, sometimes
;; you want a side effect when a transaction succeeds. Agents provide
;; a solution. If you send exactly once, if and only if the
;; transaction succeeds.

;; As an example of where this would be useful, consider an agent that
;; writes to a file when a transaction succeeds. You should combine
;; such an agent with the chat example from Section , commute.

;; the new function has one other cirtical difference : it calls
;; send-off in stead of send to communicate with the agent. send-off
;; is a variant of send for actions that expect to block, as a file
;; write might. send-off actions get their own expandable thread pool.
;; Never send a blocking fuction, or you may unnecessarily prevent
;; other agents from making progress.

;;    (add-msssage-with-backup (struct message "join" "message one"))
(def backup-agent (agent "output/messages-backup.clj"))
(defn add-message-with-backup [msg]
  (dosync
   (let [snapshot (commute messages conj msg)]
     (send-off backup-agent (fn [filename]
                              (println filename snapshot)
                              filename))
     snapshot)))

;; The Unified Update Model
;; As you have seen , refs, atoms, and agents al provide functions for
;; updating their state by applying a function to their previous
;; states. This unified model for handling shared state is one of the
;; central concepts of Clojure. The unified model and various
;; ancillary functions are summarized

;; The unified model update is by far the most important way to update
;; refs, atoms, and agents. The ancillary functions, on the other
;; hand, are optimizations and options that stem from the semantics
;; peculirar to each API:
;;
;; # Managing Per-Thread State with Vars

;; when you call def or defn, you create a dynamic var, often called
;; just var. In all the examples so far in the book, you pass an
;; inital value to def, which becomes the root binding for the var.
;; For example, the following code creates a root binding for foo of
;;10:
(def foo 10)

;;The binding of foo is shared by all threads. You can check the value
;;of foo on your own thread:

foo
;; you can also verify the value of foo from another thread.create a
;; new thread, passing it a function that points foo. Don't forget to
;; start the thread:

(-> (Thread. #(println (str "foo:" foo))) .start)

(-> (proxy [Thread] [] (run [] (println (str  "foo-:" foo))))
    .start)

(-> (Thread.
     (reify Runnable (run [this] (println (str "foo+:" foo)))))
    .start)

;; most vars are content to keep their root bindings forever.However ,
;; you can create a thread-local binding for a var with the binding
;; macro:
;;    (binding [bindings] & body )
;; bindings have dynamic scope. In other words, a binding is visible
;; any where a thread's execution takes it, until the thread exists
;; the scope where the binding began. A binding is not visble to any
;; other threads.

;; Structurally, a binding looks a lot like a let. Create a
;; thread-local binding for foo and check its value:

(declare ^:dynamic foo)
(binding [foo 42] foo)

;; As you can see, the let has no effect outside its own form, so the
;; first print-foo prints the root binding 10. The binding, on other
;; hand, stays in effect down any chain of calls that begins in the
;; binding form, so the second print-foo prints bound foo.

(def v 100)
(let [v "10"] (println v))
(println v)

;; Acting at a Distance
;; vars intended for dynamic binding are sometimes called special
;; variables, it is good style to name them with leading and trailing
;; askeristk. for example, Clojure uses dynamic binding for
;; thread-wide options such as the standard I/O strams *in*, *out*,
;; and *err*. Dynamic bindings enable action at a distance. When you
;; change a dynamic binding, you can change the behavior of distant
;; functions without changing any function arguments.

;; one kind of action at a distance if temportily augmenting the
;; behavior of a function . In some language this would be classified
;; as aspect-oriented programming ; in Clojure it is simply a side
;; effect of dynamic binding.

;; As an example , imaging that you have a function that performs an
;; expensive calculation. To simulate this , write a function named
;; slow-double that sleeps for a tenth of a second and then doubles it
;; s input.

;;(def ^:dynamic slow-double (fn [n] (Thread/sleep 100) (* n 2)))

(defn ^:dynamic slow-double [n]
  (Thread/sleep 100)
  (* n 2))

(defn calls-slow-double []
  (map slow-double [1 2 3 4 1 3 4 5 4 3 2 1]))

(time (dorun (calls-slow-double)))

;; reading the code you can tell that calls-slow-double is slow
;; because it does the same work over and voer again, One times two is
;; two, no matter how many times you ask.

;; Calculations such as slow-double are good candidates for
;; memoization.
;;when you memoize a function, it keeps a cache mapping past inputs to
;; past outputs. If subsequent calls hit the cache, they will return
;; almost immediately. Thus, you are trading space(the cache) for
;; time(calculating the function again for the same inputs).

;; Clojure provides memoize, which takes a function and returns a
;; meoization of that function:
;;   (memoize function)

(defn demo-memoize []
  (time
   (dorun
    (binding [slow-double (memoize slow-double)]
      (calls-slow-double)))))

(println "/////////")
(demo-memoize)
