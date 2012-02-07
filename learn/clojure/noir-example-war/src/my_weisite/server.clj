(ns my-weisite.server
  (:require [noir.server :as server]
    [my-weisite.views.welcome] 
    [my-weisite.views.common]))
; https://groups.google.com/group/clj-noir/browse_thread/thread/40d13319f4bcb944/8d0346eb6c80fb6d?lnk=gst&q=Tomcat#8d0346eb6c80fb6d

(server/load-views "src/my_weisite/views/")

(def handler (server/gen-handler {:mode :dev
                                  :ns 'my-weisite}))                  
(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'my-weisite})))

