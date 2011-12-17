(ns minds.core
  (:use [cheshire.core]
        [hiccup.core :only (html)]
        [minds.util]))

(def freemind-version "0.8.0")
(def empty-xml (str "<map version=\"" freemind-version "\"></map>"))

(defn- as-node
  [e]
  [:node {:TEXT (e "title")}
   (for [ee (e "children")]
     (as-node ee))])

(defn- as-nodes
  [m]
  (let [root (m "root")]
    (as-node root)))

(defn ^String mindmeister-to-freemind
  [mindmeister-json]
  (if (is-empty? mindmeister-json)
    empty-xml
    (let [mindmeister-data (parse-string mindmeister-json)]
      (if (is-empty? mindmeister-data)
        empty-xml
        (html ;use html as xml
          [:map {:version freemind-version} (as-nodes mindmeister-data)])))))

(defn write-mindmeister-to-freemind
  [from-file target-file]
  (spit target-file
    (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      (mindmeister-to-freemind (slurp from-file)))))
